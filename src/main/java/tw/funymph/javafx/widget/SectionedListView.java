/* SectionedListView.java created on Aug 22, 2017
 *
 * Copyright (c) 2017 Pin-Ying Tu <dbi1463@gmail.com>
 * 
 * This file is part of SectionedListView under the MIT license.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tw.funymph.javafx.widget;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;
import static tw.funymph.javafx.widget.ImmutableSectionedListItem.getItemForRow;
import static tw.funymph.javafx.widget.ImmutableSectionedListItem.getItemForSectionHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * This class provides a list view that the items can be sectioned with a
 * section header. To keep the special data structure, please do <em>NOT</em>
 * call the {@link #setItems(ObservableList)} method to fill items, and
 * the {@link #setCellFactory(Callback)} method for cell generation.<br />
 * <br />
 * Instead, the items are filled automatically from the {@link SectionedListViewDataSource}
 * instance specified by {@link #setSectionedListViewDataSource(SectionedListViewDataSource)}.<br />
 * <br />
 * The cell for a normal item and the cell for a section header can be
 * customized with a {@link SectionedListViewCellFactory}. If the given
 * factory does not provide a cell for the section header or row, the list
 * view will provide a default cell that using a label to show the content.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
public class SectionedListView<RawItemType> extends ListView<SectionedListItem<RawItemType>> implements Callback<ListView<SectionedListItem<RawItemType>>, ListCell<SectionedListItem<RawItemType>>> {

	private String outerCellStyle;

	private SectionedListViewCellFactory<RawItemType, ?, ?> cellFactory;
	private SectionedListViewCellFactory<RawItemType, ?, ?> defaultFactory;

	private SectionedListViewDataSource<RawItemType> dataSource;
	protected ObservableList<SectionedListItem<RawItemType>> selectedItems;

	private Map<Class<?>, List<Node>> reusableCells;

	private static boolean isNotBlank(String string) {
		return string != null && string.trim().length() > 0;
	}

	/**
	 * Construct an empty <code>SectionedListView</code> instance.
	 */
	public SectionedListView() {
		this(null, null);
	}

	/**
	 * Construct a <code>SectionedListView</code> instance with the data
	 * source to fill items, and the factory to generate cells for the
	 * normal item and the section header.
	 * 
	 * @param dataSource the item data source
	 * @param cellFactory the cell factory
	 */
	public SectionedListView(SectionedListViewDataSource<RawItemType> dataSource, SectionedListViewCellFactory<RawItemType, ?, ?> cellFactory) {
		defaultFactory = new DefaultSectionedListCellFactory<RawItemType>();
		selectedItems = observableArrayList();
		reusableCells = new HashMap<>();

		setCellFactory(this);
		setSectionedListViewCellFactory(cellFactory);
		setSectionedListViewDataSource(dataSource);

		// prevent the user to replace the cell factory
		cellFactoryProperty().addListener((cellFactoryProperty, oldValue, newValue) -> {
			if (newValue != this) {
				setCellFactory(this);
			}
		});
		
		setPadding(new Insets(0.0));
		
		boundsInLocalProperty().addListener(this::removeDisabledScrollBar);
	}

	/**
	 * Reload all items from the data source.
	 */
	public void reloadData() {
		ObservableList<SectionedListItem<RawItemType>> items = observableArrayList();
		int sections = numberOfSections();
		for (int section = 0; section < sections; section++) {
			if (dataSource.hasSectionHeader(section)) {
				items.add(getItemForSectionHeader(section));
			}
			int rows = dataSource.numberOfRowsInSection(section);
			for (int row = 0; row < rows; row++) {
				IndexPath path = new ImmutableIndexPath(section, row);
				items.add(getItemForRow(section, row, dataSource.getItem(path)));
			}
		}
		setItems(items);
		recoverSelectionModel();
	}

	public int numberOfSections() {
		return dataSource != null? dataSource.numberOfSections() : 0;
	}

	/**
	 * Get the factory that generates cells for the section header and the row.
	 * 
	 * @return the factory
	 */
	public SectionedListViewCellFactory<RawItemType, ?, ?> getSectionedListViewCellFactory() {
		return cellFactory != null ? cellFactory : defaultFactory;
	}

	/**
	 * Get the data source.
	 * 
	 * @return the data source
	 */
	public SectionedListViewDataSource<RawItemType> getSectionedListViewDataSource() {
		return dataSource;
	}

	/**
	 * Set the new cell factory that generates cells for the section header
	 * and the row. This will trigger a refresh to recreate cells with the
	 * new factory.
	 *  
	 * @param factory the new factory
	 */
	public void setSectionedListViewCellFactory(SectionedListViewCellFactory<RawItemType, ?, ?> factory) {
		cellFactory = factory;
		reloadData();
	}

	/**
	 * Set the data source that provides the required data structure used by
	 * the sectioned list view. This will trigger a refresh to reload the
	 * items from the data source into the list view.
	 * 
	 * @param value the new data source
	 */
	public void setSectionedListViewDataSource(SectionedListViewDataSource<RawItemType> value) {
		dataSource = value;
		reloadData();
	}

	/**
	 * Get the selected raw objects.
	 * ，
	 * @return the list of raw objects.
	 */
	public List<RawItemType> getSelectedRawItems() {
		return getSelectionModel().getSelectedItems().stream()
			.filter(item -> item != null && !item.isHeaderItem())
			.map(item -> item.getRawItem())
			.collect(toList());
	}
	
	/**
	 * Get the non-selected raw objects.
	 * 
	 * @return the list of raw objects.
	 */
	public List<RawItemType> getNonSelectedRawItems() {
		List<RawItemType> selectedItems = getSelectedRawItems();
		return getItems().stream()
			.filter(item -> {
				return item != null && !item.isHeaderItem() && !selectedItems.contains(item);
			})
			.map(item -> item.getRawItem())
			.collect(toList());
	}
	
	/**
	 * Set the style of the outer cell. Because it's unable to call the original
	 * {@link #setCellFactory(Callback)} of the sectioned list view, this provides
	 * a chance to modify the style of the cell. The style will be applied to the
	 * cell generated by the sectioned list view and the content is filled by the
	 * {@link SectionedListViewCellFactory}. Note that this method will trigger a
	 * refresh to reload the items from the data source into the list view for
	 * applying the new style on all cells.
	 * 
	 * @param style the new style of the outer cell
	 */
	public void setOuterCellStyle(String style) {
		outerCellStyle = style;
		if (isNotBlank(outerCellStyle)) {
			reloadData();
		}
	}

	@Override
	public ListCell<SectionedListItem<RawItemType>> call(ListView<SectionedListItem<RawItemType>> param) {
		ListCell<SectionedListItem<RawItemType>> cell = new SectionedListCellContainer<RawItemType>(this);
		if (isNotBlank(outerCellStyle)) {
			cell.setStyle(outerCellStyle);
		}
		cell.setOnMouseClicked(this::updateSelectedItemsOnClick);
		return cell;
	}

	@SuppressWarnings("unchecked")
	private void updateSelectedItemsOnClick(MouseEvent event) {
		if (event.getSource() instanceof ListCell) {
			ListCell<SectionedListItem<RawItemType>> cell = (ListCell<SectionedListItem<RawItemType>>)event.getSource();
			if(cell.getItem() == null || cell.getItem().isHeaderItem()) {
				return;
			}
			
			SectionedListItem<RawItemType> toRemoved = null;
			for(SectionedListItem<RawItemType> item : selectedItems) {
				if(item.getRawItem() != null && item.getRawItem().equals(cell.getItem().getRawItem())) {
					toRemoved = item;
					break;
				}
			}
			
			if(toRemoved != null) {
				selectedItems.remove(toRemoved);
			}
			else {
				selectedItems.add(cell.getItem());
			}
		}
	}

	private void removeDisabledScrollBar(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
		Set<Node> nodes = lookupAll(".scroll-bar");
		for(Node node : nodes) {
			if (node instanceof ScrollBar) {
				ScrollBar scrollBar = (ScrollBar)node;
				if(scrollBar.getOrientation() == Orientation.HORIZONTAL && !scrollBar.isDisable()) {
					scrollBar.setDisable(true);
					scrollBar.getStyleClass().clear();
				}
			}
		}
	}

	void enqueueReusableCell(RawItemType item, Node cell) {
		List<Node> cells = reusableCells.get(item.getClass());
		if (cells == null) {
			cells = new ArrayList<>();
			reusableCells.put(item.getClass(), cells);
		}
		cells.add(cell);
	}

	Optional<Node> dequeueReusableCell(RawItemType item) {
		List<Node> cells = reusableCells.get(item.getClass());
		if (cells == null) {
			return Optional.empty();
		}
		return cells.stream().filter(node -> {
			return node.parentProperty().get() == null;
		}).findFirst();
	}

	/**
	 * Recover the selectedItems to the selection model.
	 */
	protected void recoverSelectionModel() {
		ObservableList<SectionedListItem<RawItemType>> currentItems = getItems();
		for(SectionedListItem<RawItemType> item : selectedItems) {
			for(SectionedListItem<RawItemType> currentItem : currentItems) {
				if(currentItem.getRawItem() != null && currentItem.getRawItem().equals(item.getRawItem())) {
					getSelectionModel().select(currentItems.indexOf(currentItem));
					break;
				}
			}
		}
	}
	
	/**
	 * reset the selectedItems according to new all items.
	 */
	protected void updateSelectedItemsList() {
		ObservableList<SectionedListItem<RawItemType>> newSelectedItemList = FXCollections.observableArrayList();
		for(SectionedListItem<RawItemType> selectedItem : selectedItems) {
			for(SectionedListItem<RawItemType> currentItem: getItems()) {
				if(selectedItem.getRawItem() != null && selectedItem.getRawItem().equals(currentItem.getRawItem())){
					newSelectedItemList.add(currentItem);
				}
			}
		}
		selectedItems.clear();
		selectedItems = newSelectedItemList;
	}
}

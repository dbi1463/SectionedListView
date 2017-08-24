/* SectionedListCellContainer.java created on Aug 22, 2017
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

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.lang.ref.WeakReference;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 * This class provides the container as a cell in {@link ListCell} that
 * will be formated based on the type of the item. If the item is a header
 * item, the cell will be formated as a header cell; otherwise formated
 * as a row cell.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
final class SectionedListCellContainer<RawItemType> extends ListCell<SectionedListItem<RawItemType>> {

	private WeakReference<SectionedListView<RawItemType>> listView;

	/**
	 * Construct a <code>SectionedListCell</code> instance with the host
	 * sectioned list view.
	 * 
	 * @param listView the host list view
	 */
	public SectionedListCellContainer(SectionedListView<RawItemType> listView) {
		setListView(listView);
		setPadding(new Insets(0.0));
	}

	@Override
	protected void updateItem(SectionedListItem<RawItemType> item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || getIndex() < 0) {
			setText(null);
			setGraphic(null);
			return;
		}
		if (item.isHeaderItem()) {
			setGraphic(formattedHeaderView(item));
		}
		else {
			setGraphic(formattedCell(item));
		}
	}

	public Optional<RawItemType> getRawItem() {
		return getItem() != null ? of(getItem().getRawItem()) : empty();
	}

	/**
	 * Get the formatted the cell as the header view.
	 * 
	 * @param item the header item
	 */
	private Node formattedHeaderView(SectionedListItem<RawItemType> item) {
		int section = item.getIndexPath().getSection();
		String title = listView.get().getSectionedListViewDataSource().getSectionTitle(section);
		Optional<Node> reusableHeader = listView.get().dequeueReusableHeader();
		if (reusableHeader.isPresent()) {
			Node header = reusableHeader.get();
			((SectionedListHeader)header).updateTitle(title);
			return header;
		}
		Node header = listView.get().getSectionedListViewCellFactory().getSectionHeader(this, section, title);
		listView.get().enqueueReusableHeader(header);
		return header;
	}

	/**
	 * Get the formatted the cell as the row cell.
	 * 
	 * @param item the row item
	 */
	@SuppressWarnings("unchecked")
	private Node formattedCell(SectionedListItem<RawItemType> item) {
		Optional<Node> reusableCell = listView.get().dequeueReusableCell(item.getRawItem());
		if (reusableCell.isPresent()) {
			Node cell = reusableCell.get();
			((SectionedListCell<RawItemType>)cell).updateItem(item.getRawItem());
			return cell;
		}
		Node cell = listView.get().getSectionedListViewCellFactory().getRowCell(this, item.getIndexPath(), item.getRawItem());
		listView.get().enqueueReusableCell(item.getRawItem(), cell);
		return cell;
	}

	/**
	 * Set the host list view.
	 * 
	 * @param value the list view
	 */
	private void setListView(SectionedListView<RawItemType> value) {
		listView = new WeakReference<>(value);
	}
}

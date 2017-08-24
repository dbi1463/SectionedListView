/* ImmutableSectionedListItem.java created on Aug 22, 2017
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

/**
 * This class provides the default implementation of {@link SectionedListItem}
 * to provides the information required by {@link SectionedListView}.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
class ImmutableSectionedListItem<RawItemType> implements IndexPath, SectionedListItem<RawItemType> {

	private int rowIndex;
	private int sectionIndex;
	private boolean headerItem;
	private RawItemType rawItem;

	/**
	 * Get the list item for section header with the section index. The other
	 * information will be filled with the appropriate values.
	 * 
	 * @param sectionIndex the section index
	 * @return the list item for section header
	 */
	public static <RawItemType> SectionedListItem<RawItemType> getItemForSectionHeader(int sectionIndex) {
		return new ImmutableSectionedListItem<RawItemType>(true, sectionIndex, SectionHeaderRow, null);
	}

	/**
	 * Get the list item to wrap the raw item at the index path
	 * 
	 * @param section the section index
	 * @param row the row index
	 * @param item the raw item to be wrapped
	 * @return the list item for the row
	 */
	public static <RawItemType> ImmutableSectionedListItem<RawItemType> getItemForRow(int section, int row, RawItemType item) {
		return new ImmutableSectionedListItem<RawItemType>(false, section, row, item);
	}

	/**
	 * Construct a <code>SimpleSectionedListItem</code> instance with the required
	 * information. Please use the {@link #getItemForSectionHeader(int)} method to
	 * get the item for section header and the {@link #getItemForRow(int, int, item)}
	 * method to get the list item for the row.
	 * 
	 * @param header true for section header item
	 * @param section the section index
	 * @param row the row index
	 * @param item the raw item
	 */
	private ImmutableSectionedListItem(boolean header, int section, int row, RawItemType item) {
		headerItem = header;
		rowIndex = row;
		sectionIndex = section;
		rawItem = item;
	}

	@Override
	public boolean isHeaderItem() {
		return headerItem;
	}

	@Override
	public RawItemType getRawItem() {
		return rawItem;
	}

	@Override
	public IndexPath getIndexPath() {
		return this;
	}

	@Override
	public int getSection() {
		return sectionIndex;
	}

	@Override
	public int getRow() {
		return rowIndex;
	}
}

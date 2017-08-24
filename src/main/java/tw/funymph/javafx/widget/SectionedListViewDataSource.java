/* package-info.java created on Aug 22, 2017
 *
 * Copyright (c) <2017> Pin-Ying Tu <dbi1463@gmail.com>
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
 * This interface defines the data source for the {@link SectionedListView}.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
public interface SectionedListViewDataSource<RawItemType> {

	/**
	 * Get the number of the sections that will be shown in the list.
	 * 
	 * @return the number of the sections
	 */
	public int numberOfSections();

	/**
	 * Get whether the indexed section need a header view or not.
	 * 
	 * @param section the section index; starting from 0
	 * @return true if the section that needs a header view
	 */
	public boolean hasSectionHeader(int section);

	/**
	 * Get the title of the specified section.
	 * 
	 * @param section the section index; starting from 0
	 * @return the section title
	 */
	public String getSectionTitle(int section);

	/**
	 * Get the number of the rows in the given section.
	 * 
	 * @param section the section index; starting from 0
	 * @return the number of the rows
	 */
	public int numberOfRowsInSection(int section);

	/**
	 * Get the item of the row at the specified index path.
	 * 
	 * @param path the index path
	 * @return the item of the row
	 */
	public RawItemType getItem(IndexPath path);
}

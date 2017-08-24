/* IndexPath.java created on Aug 22, 2017
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
 * Each item in the {@link SectionedListView} is associated with an index
 * path to indicate the location in the view.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
public interface IndexPath {

	/**
	 * The row index that indicates this item is section header, not a normal row.
	 */
	public static final int SectionHeaderRow = -1;

	/**
	 * Get the index of the section that the item belongs.
	 * 
	 * @return the section index
	 */
	public int getSection();

	/**
	 * Get the index of the item in its container section. Note that
	 * if the item is a header item ({@link #isHeaderItem()} returns
	 * {@code true}), the row index should be {@link #SectionHeaderRow}.
	 * 
	 * @return the row index
	 */
	public int getRow();
}

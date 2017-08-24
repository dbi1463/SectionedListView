/* SectionedListItem.java created on Aug 22, 2017
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
 * This internal interface defines the required information for a item can
 * be displayed in the {@link SectionedListView}.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
interface SectionedListItem<RawItemType> {

	/**
	 * Get whether the item is a header item or not.
	 * 
	 * @return true if the item is a header item
	 */
	boolean isHeaderItem();

	/**
	 * Get the index path of the item.
	 * 
	 * @return the index path
	 */
	IndexPath getIndexPath();

	/**
	 * Get the wrapped raw item.
	 * 
	 * @return the raw item
	 */
	RawItemType getRawItem();
}

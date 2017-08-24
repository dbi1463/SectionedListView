/* ImmutableIndexPath.java created on Aug 22, 2017
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
 * This class provides an index path that is immutable after creation.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
final class ImmutableIndexPath implements IndexPath {

	private final int section;
	private final int row;

	/**
	 * Construct a <code>ImmutableIndexPath</code> with the section index
	 * and the row index.
	 * 
	 * @param sectionIndex the section index
	 * @param rowIndex the row index
	 */
	ImmutableIndexPath(int sectionIndex, int rowIndex) {
		row = rowIndex;
		section = sectionIndex;
	}

	@Override
	public int getSection() {
		return section;
	}

	@Override
	public int getRow() {
		return row;
	}
}

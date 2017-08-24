/* SectionedListViewCellFactory.java created on Aug 22, 2017
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

import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 * This interface defines the methods to generate cells displayed
 * in {@link SectionedListView}.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
public interface SectionedListViewCellFactory<RawItemType, CellType extends Node & SectionedListCell<RawItemType>, HeaderType extends Node & SectionedListHeader> {

	/**
	 * Get the row cell for the item specified by the section index and row
	 * index. The result of this method will be the graphic of the outer cell
	 * by calling the {@link javafx.scene.control.ListCell#setGraphic(Node)}
	 * method.
	 * 
	 * @param cell the actual list cell (can customize its appearance)
	 * @param path the index path
	 * @param item the item displayed
	 * @return the cell
	 */
	CellType getRowCell(ListCell<?> cell, IndexPath path, RawItemType item);

	/**
	 * Get the header cell for the section title specified by the section index.
	 * The result of this method will be the graphic of the outer cell by calling
	 * the {@link javafx.scene.control.ListCell#setGraphic(Node)} method.
	 * 
	 * @param cell the actual list cell (can customize its appearance)
	 * @param section the section index
	 * @param title the title in the header
	 * @return the header cell
	 */
	HeaderType getSectionHeader(ListCell<?> cell, int section, String title);
}

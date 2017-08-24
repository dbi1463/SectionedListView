package tw.funymph.javafx.widget;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/* SectionedListDemo.java created on Aug 22, 2017
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

/**
 * @author Spirit
 * @version
 * @since
 */
public class SectionedListDemo extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SectionedListViewDataSource<String> source = new SectionedListViewDataSource<String>() {

			@Override
			public int numberOfSections() {
				return 100;
			}

			@Override
			public boolean hasSectionHeader(int section) {
				return true;
			}

			@Override
			public String getSectionTitle(int section) {
				return String.format("section %d", section);
			}

			@Override
			public int numberOfRowsInSection(int section) {
				return 1000;
			}

			@Override
			public String getItem(IndexPath path) {
				return String.format("Row %d in %d", path.getRow(), path.getSection());
			}
		};
		SectionedListView<String> listView = new SectionedListView<>(source, null);
		StackPane root = new StackPane();
		root.getChildren().add(listView);
		Scene scene = new Scene(root, 250, 500);
        primaryStage.setTitle("SectionedListView Demo");
        primaryStage.setScene(scene);
		primaryStage.show();
	}
}

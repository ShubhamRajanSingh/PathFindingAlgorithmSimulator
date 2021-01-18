package application;


/*
 * Author : Shubham R Singh
 * Date: 18/01/2021
 */
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;





public class FxmlLoader {

	private Pane view;

	
	/*
	 * this method is used to load the Grid.
	 * 
	 */
	public Pane getPage(String filename) {

		try {

			URL fileUrl = Main.class.getResource("/Grid/" + filename + ".fxml");
			if (fileUrl == null) {

				throw new java.io.FileNotFoundException("Fxml File Not Found");
			}
			view = new FXMLLoader().load(fileUrl);
		} catch (Exception e) {
			System.out.println(e);

		}
		return view;
	}

}

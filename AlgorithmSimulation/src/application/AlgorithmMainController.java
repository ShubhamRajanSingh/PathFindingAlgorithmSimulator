package application;


/*
 * Author : Shubham R Singh
 * Date: 18/01/2021
 */
import java.net.URL;
import java.util.ResourceBundle;

import Grid.GridController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AlgorithmMainController implements Initializable {

	@FXML
	private BorderPane borderPane;
	@FXML
	private VBox vBox;
	@FXML
	private ComboBox<String> tileType;
	@FXML
	private ComboBox<String> chooseAlgorithm;
	@FXML
	private Label totalTile;
	private static Label dubtotalTile;
	@FXML
	private Label totalWall;
	private static Label dubtotalWall;
	@FXML
	private Label visitedTile;
	private static Label dubVisitedTile;
	@FXML
	private Label pathFound;
	private static Label dubPathFound;
	@FXML
	private Label pathCost;
	private static Label dubPathCost;
	@FXML
	private Label time;
	private static Label dubTime;

	@FXML
	private Button run;
	@FXML
	private Button clear;
	@FXML
	private Button clearMaze;
	@FXML
	private Button exit;
	@FXML
	private Button createMaze;

	private static String currenttileType = null;
	ObservableList<String> tiletypelist = FXCollections.observableArrayList("START", "END", "WALL", "CLEAR");
	ObservableList<String> algorithmList = FXCollections.observableArrayList("A*", "DIJKSTRA's");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tileType.setItems(tiletypelist);
		tileType.getSelectionModel().selectFirst();
		chooseAlgorithm.setItems(algorithmList);
		chooseAlgorithm.getSelectionModel().selectFirst();
		FxmlLoader obj = new FxmlLoader();
		Pane view = obj.getPage("Grid");
		borderPane.setCenter(view);
		currenttileType = new String(tileType.getValue());
		dubtotalTile = totalTile;
		dubtotalWall = totalWall;
		dubVisitedTile = visitedTile;
		dubPathFound = pathFound;
		dubPathCost = pathCost;
		dubTime = time;
	}

	public static Label gettotaltile() {
		return dubtotalTile;

	}

	public static Label getDubtotalWall() {
		return dubtotalWall;
	}

	public static Label getDubVisitedTile() {
		return dubVisitedTile;
	}

	public static Label getDubPathFound() {
		return dubPathFound;
	}

	public static Label getDubPathCost() {
		return dubPathCost;
	}

	public static Label getDubTime() {
		return dubTime;
	}

	@FXML
	public void clearHandler(ActionEvent event) {
		FxmlLoader obj = new FxmlLoader();
		Pane view = obj.getPage("Grid");
		borderPane.setCenter(view);
	}

	@FXML
	public void clearMazeHandler(ActionEvent event) {

		GridController.clearMaze();

	}

	@FXML
	public void createMazeHandler(ActionEvent event) {
		FxmlLoader obj = new FxmlLoader();
		Pane view = obj.getPage("Grid");
		borderPane.setCenter(view);
		GridController.createMaze();

	}

	@FXML
	public void exitHandler(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	public void setTileType(ActionEvent event) {

		currenttileType = new String(tileType.getValue());
	}

	public static String getTileType() {

		return currenttileType;
	}

	@FXML
	public void runHandler(ActionEvent event) {
		if (chooseAlgorithm.getValue().equals("DIJKSTRA's")) {
			GridController.Dijkstra();
		} else if (chooseAlgorithm.getValue().equals("A*")) {
			GridController.AStar();
		}

	}

}

package Grid;

/*
 * Author : Shubham R Singh
 * Date: 18/01/2021
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.ResourceBundle;

import application.AlgorithmMainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridController implements Initializable {
	@FXML
	private GridPane MainGrid;

	public static Rectangle rect[][] = new Rectangle[33][25];
	public static int visited[][] = new int[33][25];

	public static int startCol = 0;

	public static int startRow = 0;

	public static int endCol = 0;

	public static int endRow = 0;
	public static int Walls = 0;
	public static int visitedWalls = 0;

	public static Color rectColor = Color.rgb(221, 225, 225);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearGrid();
		MainGrid.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: #B0AEAE;");

	}

	/*
	 * This Method creates a new Grid.
	 */
	public void clearGrid() {

		for (int i = 0; i < 33; i++) {
			for (int j = 0; j < 25; j++) {

				addRect(i, j);
			}

		}
	}

	/*
	 * this method adds rectangle(Tile) in the grid
	 */
	public void addRect(int i, int j) {
		rect[i][j] = new Rectangle();
		rect[i][j].setWidth(25);
		rect[i][j].setHeight(24);
		rect[i][j].setFill(rectColor);
		rect[i][j].setStroke(Color.BLACK);
		visited[i][j] = -1;

		rect[i][j].setOnMouseClicked(e -> {

			// sets the start and end tile
			if (AlgorithmMainController.getTileType().equals("START")) {

				if (rect[i][j].getFill() != Color.BLACK) {
					for (int k = 0; k < 33; k++) {
						for (int l = 0; l < 25; l++) {
							if (rect[k][l].getFill() == Color.GREEN) {
								rect[k][l].setFill(rectColor);
							}

						}

					}
					startCol = i;
					startRow = j;
					rect[i][j].setFill(Color.GREEN);
				}

			} else if (AlgorithmMainController.getTileType().equals("END")) {
				if (rect[i][j].getFill() != Color.BLACK) {
					for (int k = 0; k < 33; k++) {
						for (int l = 0; l < 25; l++) {
							if (rect[k][l].getFill() == Color.RED) {
								rect[k][l].setFill(rectColor);
							}

						}

					}

					endCol = i;
					endRow = j;
					rect[i][j].setFill(Color.RED);
				}
			} else if (AlgorithmMainController.getTileType().equals("WALL")) {
				rect[i][j].setFill(Color.BLACK);
			} else {
				rect[i][j].setFill(rectColor);

			}
		});
		MainGrid.add(rect[i][j], i, j);

	}

	/*
	 * this creates the Maze
	 */
	public static void createMaze() {
		Random random = new Random();
		Walls = 0;
		for (int i = 0; i < 33; i++) {
			for (int j = 0; j < 25; j++) {
				if ((random.nextInt(3) + 0) == 1) {
					rect[i][j].setFill(Color.BLACK);
					Walls++;
				}

			}

		}

	}

	/*
	 * This Method Clears the existing Maze
	 * 
	 */
	public static void clearMaze() {
		visitedWalls = 0;
		visited[startCol][startRow] = -1;
		visited[endCol][endRow] = -1;
		for (int i = 0; i < 33; i++) {
			for (int j = 0; j < 25; j++) {
				if (rect[i][j].getFill() != Color.BLACK && rect[i][j].getFill() != Color.GREEN
						&& rect[i][j].getFill() != Color.RED) {
					rect[i][j].setFill(rectColor);
					visited[i][j] = -1;
				}

			}

		}

	}

	/*
	 * Method to run Dijkstra's Algorithm
	 * 
	 */
	public static void Dijkstra() {
		clearMaze();
		long startTime = System.nanoTime();
		List<Rectangle> path = new ArrayList<Rectangle>();
		HashMap<Rectangle, List<Rectangle>> allnode = new HashMap<Rectangle, List<Rectangle>>();
		Queue<Integer> colQueue = new LinkedList<Integer>();
		Queue<Integer> rowQueue = new LinkedList<Integer>();

		Rectangle temp = rect[startCol][startRow];
		path.add(rect[startCol][startRow]);

		allnode.put(temp, path);
		visited[startCol][startRow] = 1;
		colQueue.add(startCol);
		rowQueue.add(startRow);

		while (colQueue.size() != 0) {
			int i = colQueue.poll();
			int j = rowQueue.poll();

			path = new ArrayList<Rectangle>();
			visited[i][j] = 1;
			if (rect[i][j].getFill() != Color.RED && rect[i][j].getFill() != Color.GREEN)
				rect[i][j].setFill(Color.BROWN);
			if ((i + 1) <= 32 && rect[i + 1][j].getFill() != Color.BLACK && visited[i + 1][j] != 1) {

				path = new ArrayList<Rectangle>(allnode.get(rect[i][j]));
				path.add(rect[i + 1][j]);
				if (allnode.get(rect[i + 1][j]) != null) {
					List<Rectangle> templist = new ArrayList<Rectangle>();
					templist = allnode.get(rect[i + 1][j]);
					if (path.size() < templist.size()) {
						allnode.replace(rect[i + 1][j], path);
						colQueue.add(i + 1);
						rowQueue.add(j);

					}

				} else {
					allnode.put(rect[i + 1][j], path);
					colQueue.add(i + 1);
					rowQueue.add(j);

				}
			}
			if ((i - 1) >= 0 && rect[i - 1][j].getFill() != Color.BLACK && visited[i - 1][j] != 1) {

				path = new ArrayList<Rectangle>(allnode.get(rect[i][j]));
				path.add(rect[i - 1][j]);
				if (allnode.get(rect[i - 1][j]) != null) {
					List<Rectangle> templist = new ArrayList<Rectangle>();
					templist = allnode.get(rect[i - 1][j]);
					if (path.size() < templist.size()) {
						allnode.replace(rect[i - 1][j], path);
						colQueue.add(i - 1);
						rowQueue.add(j);

					}

				} else {
					allnode.put((rect[i - 1][j]), path);
					colQueue.add(i - 1);
					rowQueue.add(j);

				}
			}
			if ((j + 1) <= 24 && rect[i][j + 1].getFill() != Color.BLACK && visited[i][j + 1] != 1) {

				path = new ArrayList<Rectangle>(allnode.get(rect[i][j]));
				path.add(rect[i][j + 1]);

				if (allnode.get(rect[i][j + 1]) != null) {
					List<Rectangle> templist = new ArrayList<Rectangle>();
					templist = allnode.get(rect[i][j + 1]);
					if (path.size() < templist.size()) {
						allnode.replace(rect[i][j + 1], path);
						colQueue.add(i);
						rowQueue.add(j + 1);

					}

				} else {
					allnode.put(rect[i][j + 1], path);
					colQueue.add(i);
					rowQueue.add(j + 1);

				}
			}
			if ((j - 1) >= 0 && rect[i][j - 1].getFill() != Color.BLACK && visited[i][j - 1] != 1) {

				path = new ArrayList<Rectangle>(allnode.get(rect[i][j]));
				path.add(rect[i][j - 1]);
				if (allnode.get(rect[i][j - 1]) != null) {
					List<Rectangle> templist = new ArrayList<Rectangle>();
					templist = allnode.get(rect[i][j - 1]);
					if (path.size() < templist.size()) {
						allnode.replace(rect[i][j - 1], path);
						colQueue.add(i);
						rowQueue.add(j - 1);

					}

				} else {
					allnode.put(rect[i][j - 1], path);
					colQueue.add(i);
					rowQueue.add(j - 1);

				}
			}

		}

		if (allnode.get(rect[endCol][endRow]) != null) {
			path = allnode.get(rect[endCol][endRow]);
			for (Rectangle rec : path) {
				if (rect[endCol][endRow] != rec && rec != rect[startCol][startRow])
					rec.setFill(Color.DARKCYAN);

			}
		}

		int cost = -1;
		String found = "FALSE";
		if (allnode.get(rect[endCol][endRow]) != null) {
			cost = path.size();
			found = "TRUE";
		}
		long stopTime = System.nanoTime();
		long time = stopTime - startTime;
		setAttributes(allnode.size() + "", found + "", cost + "", time + "");
	}

	/*
	 * This Method Sets the Summary
	 * 
	 */
	public static void setAttributes(String Visted, String PathFound, String PathCost, String Time) {
		Label totaltile = AlgorithmMainController.gettotaltile();
		Label totalWalls = AlgorithmMainController.getDubtotalWall();
		Label pathFond = AlgorithmMainController.getDubPathFound();
		Label pathcost = AlgorithmMainController.getDubPathCost();
		Label totaltime = AlgorithmMainController.getDubTime();
		Label visited = AlgorithmMainController.getDubVisitedTile();
		totaltile.setText("825");

		visited.setText(Visted);
		pathFond.setText(PathFound);
		pathcost.setText(PathCost);
		totalWalls.setText(Walls + "");
		totaltime.setText(Time + " nanoSec");

	}

	/*
	 * This Method is used to calculate Manhattan Distance
	 * 
	 */
	public static int ManhattanDistance(int x1, int x2, int y1, int y2) {

		double dist = 0;
		if (x2 >= x1 && y2 >= y1) {

			dist = (x2 - x1) + (y2 - y1);

		} else if (x2 <= x1 && y2 <= y1) {
			dist = (x1 - x2) + (y1 - y2);

		} else if (x2 >= x1 && y2 <= y1) {
			dist = (x2 - x1) + (y1 - y2);

		} else if (x2 <= x1 && y2 >= y1) {
			dist = (x1 - x2) + (y2 - y1);

		}

		return (int) dist;
	}

	/*
	 * 
	 * This Method is Used for Sorting NOTE: Insertion Sort is Used
	 */
	public static void sort(ArrayList<Integer> arrF, ArrayList<Integer> arrH, ArrayList<Integer> arrG,
			ArrayList<Rectangle> arrRec, ArrayList<Integer> colInd, ArrayList<Integer> rowInd) {
		int n = arrF.size();

		for (int i = 1; i < n; i++) {

			Rectangle recttemp;
			int temp;
			int j = i - 1;

			while (j >= 0 && arrF.get(j) >= arrF.get(j + 1)) {
				if (arrF.get(j) == arrF.get(j + 1)) {
					if (arrH.get(j) >= arrH.get(j + 1)) {
						temp = arrF.get(j + 1);
						arrF.set(j + 1, arrF.get(j));
						arrF.set(j, temp);
						temp = arrH.get(j + 1);
						arrH.set(j + 1, arrH.get(j));
						arrH.set(j, temp);
						temp = arrG.get(j + 1);
						arrG.set(j + 1, arrG.get(j));
						arrG.set(j, temp);
						temp = rowInd.get(j + 1);
						rowInd.set(j + 1, rowInd.get(j));
						rowInd.set(j, temp);
						temp = colInd.get(j + 1);
						colInd.set(j + 1, colInd.get(j));
						colInd.set(j, temp);

						recttemp = arrRec.get(j + 1);
						arrRec.set(j + 1, arrRec.get(j));
						arrRec.set(j, recttemp);

					}

				} else {
					temp = arrF.get(j + 1);
					arrF.set(j + 1, arrF.get(j));
					arrF.set(j, temp);

					temp = arrH.get(j + 1);
					arrH.set(j + 1, arrH.get(j));
					arrH.set(j, temp);

					temp = arrG.get(j + 1);
					arrG.set(j + 1, arrG.get(j));
					arrG.set(j, temp);

					temp = rowInd.get(j + 1);
					rowInd.set(j + 1, rowInd.get(j));
					rowInd.set(j, temp);

					temp = colInd.get(j + 1);
					colInd.set(j + 1, colInd.get(j));
					colInd.set(j, temp);

					recttemp = arrRec.get(j + 1);
					arrRec.set(j + 1, arrRec.get(j));
					arrRec.set(j, recttemp);

				}
				j = j - 1;
			}

		}
	}

	/*
	 * This Method is used to run A* Algorithm
	 * 
	 * 
	 * 
	 */
	public static void AStar() {
		clearMaze();
		long startTime = System.nanoTime();
		int flag = 0;
		int gCost, fCost, hCost;
		Rectangle currentRect, tempRect;
		HashMap<Rectangle, List<Rectangle>> allnode = new HashMap<Rectangle, List<Rectangle>>();
		ArrayList<Integer> listF = new ArrayList<Integer>();
		ArrayList<Integer> listH = new ArrayList<Integer>();
		ArrayList<Integer> listG = new ArrayList<Integer>();
		ArrayList<Integer> colIndex = new ArrayList<Integer>();
		ArrayList<Integer> rowIndex = new ArrayList<Integer>();
		ArrayList<Rectangle> listRect = new ArrayList<Rectangle>();
		List<Rectangle> path = new ArrayList<Rectangle>();
		visited[startCol][startRow] = 1;
		int i = startCol, j = startRow;
		currentRect = rect[startCol][startRow];
		path.add(currentRect);
		allnode.put(currentRect, path);
		listF.add(0);
		listH.add(0);
		listG.add(0);
		colIndex.add(startCol);
		rowIndex.add(startRow);
		listRect.add(currentRect);
		int parentG;

		while (flag != 1 && listRect.size() > 0) {

			currentRect = listRect.get(0);
			i = colIndex.get(0);
			j = rowIndex.get(0);
			visited[i][j] = 1;
			parentG = listG.get(0);

			listRect.remove(0);
			colIndex.remove(0);
			rowIndex.remove(0);
			listF.remove(0);
			listH.remove(0);
			listG.remove(0);

			if ((i + 1) <= 32 && rect[i + 1][j].getFill() != Color.BLACK && visited[i + 1][j] != 1) {

				tempRect = rect[i + 1][j];
				if (tempRect.getFill() != Color.RED && tempRect.getFill() != Color.GREEN)
					tempRect.setFill(Color.BROWN);

				gCost = parentG + 1;
				hCost = ManhattanDistance(i + 1, endCol, j, endRow);
				fCost = gCost + hCost;

				if (allnode.get(tempRect) != null) {
					int m = listRect.indexOf(tempRect);
					if (listF.get(m) > fCost) {
						path = new ArrayList<Rectangle>(allnode.get(currentRect));
						path.add(tempRect);
						listF.set(m, fCost);
						listH.set(m, hCost);
						listG.set(m, gCost);
					} else if (listF.get(m) == fCost) {

						if (listH.get(m) >= hCost) {
							path = new ArrayList<Rectangle>(allnode.get(currentRect));
							path.add(tempRect);
							listF.set(m, fCost);
							listH.set(m, hCost);
							listG.set(m, gCost);

						}
					}

					sort(listF, listH, listG, listRect, colIndex, rowIndex);

				} else {
					path = new ArrayList<Rectangle>(allnode.get(currentRect));
					path.add(tempRect);
					allnode.put(tempRect, path);

					if (tempRect.getFill() == Color.RED) {
						flag = 1;
						break;
					}

					listF.add(fCost);
					listH.add(hCost);
					listG.add(gCost);

					colIndex.add(i + 1);
					rowIndex.add(j);
					listRect.add(tempRect);
					sort(listF, listH, listG, listRect, colIndex, rowIndex);
				}

			}

			if ((i - 1) >= 0 && rect[i - 1][j].getFill() != Color.BLACK && visited[i - 1][j] != 1) {

				tempRect = rect[i - 1][j];
				if (tempRect.getFill() != Color.RED && tempRect.getFill() != Color.GREEN)
					tempRect.setFill(Color.BROWN);

				gCost = parentG + 1;
				hCost = ManhattanDistance(i - 1, endCol, j, endRow);
				fCost = gCost + hCost;

				if (allnode.get(tempRect) != null) {
					int m = listRect.indexOf(tempRect);
					if (listF.get(m) > fCost) {
						path = new ArrayList<Rectangle>(allnode.get(currentRect));
						path.add(tempRect);
						listF.set(m, fCost);
						listH.set(m, hCost);
						listG.set(m, gCost);
					} else if (listF.get(m) == fCost) {

						if (listH.get(m) >= hCost) {
							path = new ArrayList<Rectangle>(allnode.get(currentRect));
							path.add(tempRect);
							listF.set(m, fCost);
							listH.set(m, hCost);
							listG.set(m, gCost);

						}
					}

					sort(listF, listH, listG, listRect, colIndex, rowIndex);

				} else {
					path = new ArrayList<Rectangle>(allnode.get(currentRect));
					path.add(tempRect);
					allnode.put(tempRect, path);

					if (tempRect.getFill() == Color.RED) {
						flag = 1;
						break;
					}

					listF.add(fCost);
					listH.add(hCost);
					listG.add(gCost);

					colIndex.add(i - 1);
					rowIndex.add(j);
					listRect.add(tempRect);
					sort(listF, listH, listG, listRect, colIndex, rowIndex);
				}

			}
			if ((j + 1) <= 24 && rect[i][j + 1].getFill() != Color.BLACK && visited[i][j + 1] != 1) {

				tempRect = rect[i][j + 1];
				if (tempRect.getFill() != Color.RED && tempRect.getFill() != Color.GREEN)
					tempRect.setFill(Color.BROWN);

				gCost = parentG + 1;
				hCost = ManhattanDistance(i, endCol, j + 1, endRow);
				fCost = gCost + hCost;

				if (allnode.get(tempRect) != null) {
					int m = listRect.indexOf(tempRect);
					if (listF.get(m) > fCost) {
						path = new ArrayList<Rectangle>(allnode.get(currentRect));
						path.add(tempRect);
						listF.set(m, fCost);
						listH.set(m, hCost);
						listG.set(m, gCost);
					} else if (listF.get(m) == fCost) {

						if (listH.get(m) >= hCost) {
							path = new ArrayList<Rectangle>(allnode.get(currentRect));
							path.add(tempRect);
							listF.set(m, fCost);
							listH.set(m, hCost);
							listG.set(m, gCost);

						}
					}

					sort(listF, listH, listG, listRect, colIndex, rowIndex);

				} else {
					path = new ArrayList<Rectangle>(allnode.get(currentRect));
					path.add(tempRect);
					allnode.put(tempRect, path);

					if (tempRect.getFill() == Color.RED) {
						flag = 1;
						break;
					}

					listF.add(fCost);
					listH.add(hCost);
					listG.add(gCost);

					colIndex.add(i);
					rowIndex.add(j + 1);
					listRect.add(tempRect);
					sort(listF, listH, listG, listRect, colIndex, rowIndex);
				}

			}
			if ((j - 1) >= 0 && rect[i][j - 1].getFill() != Color.BLACK && visited[i][j - 1] != 1) {
				tempRect = rect[i][j - 1];
				if (tempRect.getFill() != Color.RED && tempRect.getFill() != Color.GREEN)
					tempRect.setFill(Color.BROWN);

				gCost = parentG + 1;
				hCost = ManhattanDistance(i, endCol, j - 1, endRow);
				fCost = gCost + hCost;

				if (allnode.get(tempRect) != null) {
					int m = listRect.indexOf(tempRect);
					if (listF.get(m) > fCost) {
						path = new ArrayList<Rectangle>(allnode.get(currentRect));
						path.add(tempRect);
						listF.set(m, fCost);
						listH.set(m, hCost);
						listG.set(m, gCost);
					} else if (listF.get(m) == fCost) {

						if (listH.get(m) >= hCost) {
							path = new ArrayList<Rectangle>(allnode.get(currentRect));
							path.add(tempRect);
							listF.set(m, fCost);
							listH.set(m, hCost);
							listG.set(m, gCost);

						}
					}

					sort(listF, listH, listG, listRect, colIndex, rowIndex);

				} else {
					path = new ArrayList<Rectangle>(allnode.get(currentRect));
					path.add(tempRect);
					allnode.put(tempRect, path);

					if (tempRect.getFill() == Color.RED) {
						flag = 1;
						break;
					}

					listF.add(fCost);
					listH.add(hCost);
					listG.add(gCost);

					colIndex.add(i);
					rowIndex.add(j - 1);
					listRect.add(tempRect);
					sort(listF, listH, listG, listRect, colIndex, rowIndex);
				}

			}

		}
		if (allnode.get(rect[endCol][endRow]) != null) {
			path = allnode.get(rect[endCol][endRow]);
			for (Rectangle rec : path) {
				if (rect[endCol][endRow] != rec && rec != rect[startCol][startRow])
					rec.setFill(Color.DARKCYAN);

			}
		}

		long endTime = System.nanoTime();
		long time = endTime - startTime;
		int cost = -1;
		String found = "FALSE";
		if (allnode.get(rect[endCol][endRow]) != null) {

			cost = path.size();
			found = "TRUE";
		}

		setAttributes(allnode.size() + "", found + "", cost + "", time + "");

	}

}

//Aaron Felkai
//Recursive Maze Solver
//Meanings of values in maze: 0 = wall, 1 = open path, 2 = current path, 3 = dead end/backtracked path

import java.io.*;
import java.util.Arrays;

public class MazeSolver {

	Maze mazeInstance = new Maze(new Coordinate(40, 17), new Coordinate(0, 27), readMaze());	//Make new instance of a maze to interact with using a given start point and end point (based on index not natural numbers), and a maze to solve

	public int[][] readMaze() {	//Method for reading a .csv file of a maze with values separated by commas (if looking at .csv in excel, each value has its own cell)
		int[][] maze = new int[41][41];
		File csvFile = new java.io.File("src/Maze.csv");
		int maxColumns = 41;
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		int row = 1;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) != null) {
				String[] column = line.split(csvSplitBy);
				if(row > 1) {
					int columns = 0;
					for(int i = 1; i < maxColumns; i++) {
						maze[row-2][columns] = Integer.parseInt(column[i]);
						columns++;
					}
				}
				row++;
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return maze;
	}

	public int[][] solveMaze() {	//Recursive method for solving a maze with a given start and end point
		if(mazeInstance.checkSurroundings("up") == 1) mazeInstance.goUp();
		else if(mazeInstance.checkSurroundings("left") == 1) mazeInstance.goLeft();
		else if(mazeInstance.checkSurroundings("down") == 1)  mazeInstance.goDown();
		else if(mazeInstance.checkSurroundings("right") == 1) mazeInstance.goRight();

		if(mazeInstance.getStart().getXCoord() == mazeInstance.getExit().getXCoord() && mazeInstance.getStart().getYCoord() == mazeInstance.getExit().getYCoord()) {	//Break out of recursion if base case is met
			mazeInstance.getMaze()[mazeInstance.getExit().getXCoord()][mazeInstance.getExit().getYCoord()] = 2;
			return mazeInstance.getMaze();
		}

		mazeInstance.deadEndCheck(mazeInstance.checkSurroundings("up"), mazeInstance.checkSurroundings("left"), mazeInstance.checkSurroundings("down"), mazeInstance.checkSurroundings("right"));	//Check for dead ends/backtracked path

		return solveMaze();
	}

	public String toString(int[][] maze) {	//Print 2 dimensional arrays nicely
		String output = "";

		for(int[] row : maze)
			output += Arrays.toString(row) + "\n";

		return output;
	}

	public static void main(String[] args) {

		MazeSolver mazeSolverInstance = new MazeSolver();	//Must create instance of MazeSolver class because main method is a static method, and the class and its attributes and methods are not

		System.out.println(mazeSolverInstance.toString(mazeSolverInstance.readMaze()));		//Read the original maze and print it
		System.out.println(mazeSolverInstance.toString(mazeSolverInstance.solveMaze()));	//Solve the original maze and print the solved maze
	}
}
//Aaron Felkai

import java.util.ArrayList;
import java.util.Stack;

public class Maze {

	Coordinate start;
	Coordinate exit;
	int[][] maze;
	Stack<Coordinate> pathTaken = new Stack<Coordinate>();
	ArrayList<Coordinate> visited = new ArrayList<Coordinate>();

	Maze(Coordinate begin, Coordinate end, int[][] initialMaze) {	//Constructor for initializing start and end points and initial maze
		start = begin;
		exit = end;
		maze = initialMaze;
	}

	public int checkSurroundings(String direction) {	//Method for checking surrounding values for deciding where to move
		int valueOfAdjacentCell = 0;

		if(direction == "up") valueOfAdjacentCell = maze[start.getXCoord()-1][start.getYCoord()];
		else if(direction == "down") valueOfAdjacentCell = maze[start.getXCoord()+1][start.getYCoord()];
		else if(direction == "left") valueOfAdjacentCell = maze[start.getXCoord()][start.getYCoord()-1];
		else if(direction == "right") valueOfAdjacentCell = maze[start.getXCoord()][start.getYCoord()+1];

		return valueOfAdjacentCell;
	}

	public int deadEndCheck(int up, int left, int down, int right) {	/*  Method for checking how many walls/dead ends/backtracked paths  */
		int deadEndChecker = 0;										   /* are around the current point. If the current point is surrounded */
		if(up == 0 || up == 3) deadEndChecker++;					  /* by 3 walls/dead ends/backtracked paths, then backtrack one step  */
		if(left == 0 || left == 3) deadEndChecker++;
		if(down == 0 || down == 3) deadEndChecker++;
		if(right == 0 || right == 3)deadEndChecker++;

		if(deadEndChecker == 3) {
			getMaze()[getStart().getXCoord()][getStart().getYCoord()] = 3;
			getPathTaken().pop();
			getStart().setXCoord(getPathTaken().peek().getXCoord());
			getStart().setYCoord(getPathTaken().peek().getYCoord());
		}
		return deadEndChecker;
	}


	public void setStart(Coordinate point) {
		start = point;
	}

	public Coordinate getStart() {
		return start;
	}

	public Coordinate getExit() {
		return exit;
	}

	public Stack<Coordinate> getPathTaken() {
		return pathTaken;
	}

	public int[][] getMaze() {
		return maze;
	}


	public void goUp() {	//Method for moving up in the maze
		visited.add(new Coordinate(start.getXCoord(), start.getYCoord()));
		maze[start.getXCoord()][start.getYCoord()] = 2;
		start.setXCoord(start.getXCoord()-1);
		pathTaken.push(new Coordinate(start.getXCoord(), start.getYCoord()));
	}

	public void goDown() {	//Method for moving down in the maze
		visited.add(new Coordinate(start.getXCoord(), start.getYCoord()));
		maze[start.getXCoord()][start.getYCoord()] = 2;
		start.setXCoord(start.getXCoord()+1);
		pathTaken.push(new Coordinate(start.getXCoord(), start.getYCoord()));
	}

	public void goLeft() {	//Method for moving left in the maze
		visited.add(new Coordinate(start.getXCoord(), start.getYCoord()));
		maze[start.getXCoord()][start.getYCoord()] = 2;
		start.setYCoord(start.getYCoord()-1);
		pathTaken.push(new Coordinate(start.getXCoord(), start.getYCoord()));
	}

	public void goRight() {//Method for moving right in the maze
		visited.add(new Coordinate(start.getXCoord(), start.getYCoord()));
		maze[start.getXCoord()][start.getYCoord()] = 2;
		start.setYCoord(start.getYCoord()+1);
		pathTaken.push(new Coordinate(start.getXCoord(), start.getYCoord()));
	}
}

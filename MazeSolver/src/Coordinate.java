//Aaron Felkai

import java.util.ArrayList;

public class Coordinate {

	int xCoord;
	int yCoord;
	boolean isVisited;

	Coordinate(int x, int y) {	//Constructor for initializing the x and y coordinates for a point
		xCoord = x;
		yCoord = y;
		isVisited = true;
	}

	public boolean checkVisited(int x, int y, ArrayList<Coordinate> visited) {	//Method works, but is not used for the algorithms for solving the maze
		boolean isVisited = false;
		for(int i = 0; i < visited.size(); i++) {
			if(x == visited.get(i).getXCoord() && y == visited.get(i).getYCoord()) {
				visited.get(i).setVisited(true);
				isVisited = true;
			}
			else visited.get(i).setVisited(false);
		}
		return isVisited;
	}

	public void setVisited(boolean a) {
		isVisited = a;
	}

	public boolean getVisited() {
		return isVisited;
	}

	public int getXCoord() {
		return xCoord;
	}

	public int getYCoord() {
		return yCoord;
	}

	public void setXCoord(int x) {
		xCoord = x;
	}

	public void setYCoord(int y) {
		yCoord = y;
	}
}

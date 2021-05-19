package utils;

/**
* Name   : Coordinate
* Purpose: Represents a Graph Coordinate in the ShortestPath program
* Details: A tuple representing x and y coordinates
*
* @author  David Macababayao
* @version 1.0
* @since   2021-05-10 
*/
public class Coordinate{
	public int x;
	public int y;
	
	/*
	 * Purpose: Default Constructor 
	 * Details: Assigns a value of -1 to the x and y coordinates
	 */
	public Coordinate() {
		this.x = -1;
		this.y = -1;
	}
	
	/*
	 * Purpose: Parameterized Constructor 
	 * Details: Assigns the given integers to x and y
	 * @param : Two integers representing the x and y coordinates
	 */
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

}

package algorithm;

import java.util.ArrayList;

import utils.Coordinate;
import utils.Graph;

/**
* Name   : Algorithm
* Purpose: Algorithm Interface used in the ShortestPath program
* Details: An interface that has different implementations
* 		   Contains a QueueNode class
* 		   Contains a method calculate() which represents the algorithm to be implemented
*
* @author  David Macababayao
* @version 1.0
* @since   2021-05-10 
*/
public interface Algorithm {
	public ArrayList<Coordinate> calculate(Graph g, Coordinate s, Coordinate e);
	
	/* Used to get the 4 neighbors of a node (ONLY ADJACENT NODES) */
	static int rowNum[] = {-1, 0, 0, 1};
	static int colNum[] = {0, -1, 1, 0};
	
	/*
	 * Name   : QueueNode
	 * Purpose: A Data Structure for the queue
	 * Details: Contains Coordinate and a QueueNode representing this node's parent
	 */
	static class QueueNode
	{
		/* The coordinates of the node */
	    Coordinate pt;    
	    /* parent of this node (used to trace path) */
	    QueueNode parent; 
	    
		/*
		 * Purpose: Parameterized Constructor 
		 * Details: Assigns the given Coordinates to pt,
		 * 			sets parent to null
		 * @param : A Coordinate representing this node's Coordinates
		 */
	    public QueueNode(Coordinate coordinate)
	    {
	        pt = coordinate;
	        parent = null;
	    }
	    
		/*
		 * Name   : setParent
		 * Purpose: Assigns the given QueueNode as parent
		 * 
		 * @param : QueueNode representing the parent of this node
		 * @return: void
		 */
	    public void setParent(QueueNode par) {
	    	this.parent = par;
	    }
	};
}

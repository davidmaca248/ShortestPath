package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import utils.Coordinate;
import utils.Graph;

/**
* Name   : BFS
* Purpose: Represents an Algorithm used in the ShortestPath program 
* Details: Implements the Algorithm Interface.
* 		   Overrides Algorithm's calculate() method using Breadth First Search
*
* @author  David Macababayao
* @version 1.0
* @since   2021-05-10 
*/
public class BFS implements Algorithm{
	
	/*
	 * Name   : calculate
	 * Purpose: Calculates the shortest path from a source node to a destination node in the given graph
	 * Details: Uses Breadth First Search to calculate the shortest path
	 * 
	 * @param : Graph, Coordinate representing the source node, Coordinate representing the destination node
	 * @return: ArrayList<Coordinate> containing the Coordinates in the shortest path
	 */
	@Override
	public ArrayList<Coordinate> calculate(Graph g, Coordinate src, Coordinate dest) {
		
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		int graphSize = g.getSize();	     
		
		/* if coordinates are out of bounds */
		if(src.x < 0 || src.x > graphSize ||
			src.y < 0 || src.y > graphSize ||
			dest.x < 0 || dest.x > graphSize ||
			dest.y < 0 || dest.y > graphSize) {
			
			return path;
		}
		
		/* if coordinates are walls */
		if(!((g.getValue(src)==1) && (g.getValue(dest)==1))){
				return path;
		}

		
		/* Coordinates are valid */
		boolean [][]visited = new boolean[graphSize][graphSize];
		
	    /* Mark the source node as visited */
	    visited[src.x][src.y] = true;
	    
	    /* Create a queue for BFS */
	    Queue<QueueNode> q = new LinkedList<>();
		
	    /* Add src to queue */
	    QueueNode node = new QueueNode(src);
	    q.add(node);
		
	    /* Do a BFS starting from src node */
	    while (!q.isEmpty())
	    {
	    	/* pop top of queue */
	        QueueNode curr = q.peek();
	        
	        /* get the coordinate of the current node */
	        Coordinate pt = curr.pt;
	    
	        /* if cur.pt is the destination node, done */
	        if (pt.x == dest.x && pt.y == dest.y) {
	        	/* add the destination node to the path */
	        	path.add(pt);            
	        	
	        	QueueNode c = curr;
	        	
	        	/* if current is not the src node */
	        	while(c.pt != src) {
	        		/* keep adding the parent to path back to the src */
	        		path.add(c.parent.pt);
	        		c = c.parent;
	        	}
	        	return path;
	        }
	        
	        
	        /* If curr is not the dest */
	        q.remove();
	 
	        /* add all neighbors of curr to q if valid */
	        for (int i = 0; i < 4; i++)
	        {	             
	        	/* Get neighbor as c */
	            int row = pt.x + rowNum[i];
	            int col = pt.y + colNum[i];
	            Coordinate c = new Coordinate(row,col);
	            
	            /* if neighbor cell is valid/is a path
	               and not visited yet, enqueue it. */
	            if ((g.getValue(c)==1) && !visited[row][col])
	            {
	                /* mark neighbor as visited */
	                visited[row][col] = true;

	                /* add the parent of the queued node */
	                QueueNode adjNode = new QueueNode(c);
	                adjNode.setParent(curr);  
	                
	                /* enqueue neighbor */
	                q.add(adjNode);
	            }
	        }
	    }
	    
		return (path);
	}
}

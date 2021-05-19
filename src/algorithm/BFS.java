package algorithm;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import utils.Coordinate;

/**
* Name   : BFS
* Purpose: Represents an Algorithm used in the ShortestPath program
* Details: Implements the Algorithm abstract class.
* 		   Overrides Algorithm's calculate() method using Breadth First Search.
*
* @author  David Macababayao
* @version 1.0
* @since   2021-05-10 
*/
public class BFS extends Algorithm{

	@Override
	public void calculate() throws InterruptedException{
			
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
	        if (pt.x == dst.x && pt.y == dst.y) {
	        	/* add the destination node to the path */
	        	path.add(pt);            
	        	
	        	QueueNode c = curr;
	        	
	        	/* if current is not the src node */
	        	while(c.pt != src) {
	        		/* keep adding the parent to path back to the src */
	        		path.add(c.parent.pt);
	        		c = c.parent;
	        	}
	        	return;
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
	            if ((graph.getValue(c)==1) && !visited[row][col])
	            {
	                /* mark neighbor as visited */
	                visited[row][col] = true;

	                /* add the parent of the queued node */
	                QueueNode adjNode = new QueueNode(c);
	                adjNode.setParent(curr);  
	                
	                /* enqueue neighbor */
	                q.add(adjNode);
	                
	                /* add a delay for illustration */
	                TimeUnit.MILLISECONDS.sleep(8);
	            }
	        }
	    }
		return;
	}
}

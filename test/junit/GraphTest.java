package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.Coordinate;
import utils.Graph;

class GraphTest {

	private static Graph graph1;
	private static Graph graph2;            // empty graph
	private static int size1 = 50;
	private static int[] expected1;	        // all walls (size2)
	private static int[] expected2;         // all paths except ends (size2)
	private static int[] actual1;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		graph1 = new Graph(size1);
		graph2 = new Graph(0);
		
		expected1 = new int[graph1.getSize()]; // all walls

		expected2 = new int[graph1.getSize()];
		Arrays.fill(expected2,1);             // fill expected with paths (1s) except for ends
		expected2[0] = 0;                     // left end
		expected2[size1-1] = 0;               // right end
		
		actual1 = new int[graph1.getSize()];
	}

	@AfterEach
	void tearDown() throws Exception {
		graph1 = null;
		graph2 = null;
		expected1 = null;
		expected2 = null;
		actual1= null;
	}

	/* 
	 * Tests whether the first line of the graph is initialized to walls
	 */
	@Test
	void constructorFstLine() {
		
		// get the 1st line of the graph
		for(int i = 0; i < size1; i++) {
			actual1[i] = graph1.getValue(new Coordinate(0,i));
		}
		
		assertArrayEquals(expected1, actual1, "Should be all walls (0)");
	}

	/* 
	 * Tests whether the second line of the graph is initialized to paths
	 */
	@Test
	void constructorSecLine() {
		// actual array
		int actual1[] = new int[graph1.getSize()];
	
		// get the 2nd line of the graph (not including ends)
		for(int i = 1; i < size1-1; i++) {
			actual1[i] = graph1.getValue(new Coordinate(1,i));
		}
		
		assertArrayEquals(expected2, actual1, "Should be all paths (1) except ends");
	}
	
	/* 
	 * Tests whether the resetGraph resets the graph
	 * Exactly the same as the constructor test
	 */
	@Test
	void resetGraphFstLine() {
		int actual1[] = new int[graph1.getSize()];
		
		graph1.resetGraph();
		
		// get the 1st line of the graph
		for(int i = 0; i < size1; i++) {
			actual1[i] = graph1.getValue(new Coordinate(0,i));
		}
		
		assertArrayEquals(expected1, actual1, "Should be all walls (0)");
	}

	/* 
	 * Tests whether the setWalls() would add walls to the graph
	 */
	@Test
	void setWallsFiveWalls() {
		
		// fill in the walls of the expected array
		for(int i = 7; i < 12; i++) {
			expected2[i] = 0;
		}
		
		int row = 3;                // row to add walls on 
		ArrayList<Coordinate> walls = new ArrayList<Coordinate>();
		
		// populate row 3 on columns 7 - 11 with walls
		for(int i = 7; i < 12; i++) {
			walls.add(new Coordinate(row,i));
		}
		
		// add walls to graph
		graph1.setWalls(walls);
		
		// actual array
		int actual1[] = new int[graph1.getSize()];
		
		// fill in actual array
		for(int i = 0; i < size1; i++) {
			actual1[i] = graph1.getValue(new Coordinate(row,i));
		}
		
		assertArrayEquals(expected2, actual1, "Row 3 columns 7-11 must be paths (1)");

	}

	/* 
	 * Tests whether getSize() returns the size of graph1
	 */
	@Test
	void getSizeGraph1() {
		int actual = graph1.getSize();
		assertEquals(size1,actual, "Must return 50");
	}
	
	/* 
	 * Tests whether getSize() returns 0 for an empty graph
	 */
	@Test
	void getSizeEmptyGraph() {
		int actual = graph2.getSize();
		assertEquals(0,actual, "Must return 0");
	}
	
	/* 
	 * Tests whether getValue() returns 1 given a path
	 */
	@Test
	void getValuePath() {
		
		// (1,1) is a path
		Coordinate c = new Coordinate(1,1);
		assertEquals(1,graph1.getValue(c)," Must return 1");
	}
	
	/* 
	 * Tests whether getValue() returns 0 given a wall
	 */
	@Test
	void getValueWall() {
		
		// (0,1) is a wall
		Coordinate c = new Coordinate(0,1);
		assertEquals(0,graph1.getValue(c)," Must return 0");
	}
	
	/* 
	 * Tests whether flip() toggles the given Coordinate value (Path)
	 */
	@Test
	void flipToWall() {
		
		// (1,1) is a path (value of 1)
		Coordinate c = new Coordinate(1,1);
		graph1.flip(c);
		int actual = graph1.getValue(c);
		
		assertEquals(0,actual,"Coordinate value must be 0");
	}
	
	/* 
	 * Tests whether flip() toggles the given Coordinate value (Wall)
	 */
	@Test
	void flipToPath() {
		
		// (1,0) is a wall (value of 0)
		Coordinate c = new Coordinate(1,0);
		graph1.flip(c);
		int actual = graph1.getValue(c);
		
		assertEquals(1,actual,"Coordinate value must be 1");
	}
}

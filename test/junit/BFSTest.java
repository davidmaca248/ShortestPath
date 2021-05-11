package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algorithm.Algorithm;
import algorithm.BFS;
import utils.Coordinate;
import utils.Graph;

class BFSTest {
	private static Algorithm bfs;
	
	private static Graph graph1;
	private static Graph graph2;
	private static int size1 = 50;
	
	private static ArrayList<Coordinate> walls1;
	private static ArrayList<Coordinate> walls2;

	private static ArrayList<Coordinate> expectedPath2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		bfs = new BFS();
		
		/***********************************************************/
		/* Graph 2 (empty) */
		graph2 = new Graph(0);
		
		/* Graph 1 */
		graph1 = new Graph(size1);
		
		/***********************************************************/
		/* Walls 1 */
		walls1 = new ArrayList<Coordinate>();
		/* Add walls Rows 17 - 24 Column 15 */
		for(int i = 17; i <= 24; i++) {
			walls1.add(new Coordinate(i,15));
		}
		
		/* Walls 2 */
		walls2 = new ArrayList<Coordinate>();
		/* Add walls Rows 14 - 20 Column 20 */
		for(int i = 14; i <= 20; i++) {
			walls1.add(new Coordinate(i,20));
		}
		/* Add walls Rows 18 - 28 Column 40 */
		for(int i = 18; i < 29; i++) {
			walls1.add(new Coordinate(i,40));
		}

		/***********************************************************/
		/* Expected Path 2 (Relative to walls2 on graph1) */
		expectedPath2 = new ArrayList<Coordinate>();
		/* Row 16 Columns 5 - 20*/
		for(int i = 5; i < 21; i++) {
			expectedPath2.add(new Coordinate(16,i));
		}
		/* Column 20 Rows 16 - 30*/
		for(int i = 16; i < 31; i++) {
			expectedPath2.add(new Coordinate(i,20));
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void calculateGraph1Wall1() {
		Coordinate src1 = new Coordinate(16,5);
		Coordinate dest1 = new Coordinate(30,16);
		
		/* Apply walls1 to graph1 */
		graph1.setWalls(walls1);
		
		/* Expected Path 1 (Relative to walls1 on graph1) */
		ArrayList<Coordinate> expectedPath = new ArrayList<Coordinate>();
		/* Column 16 Rows 17 - 30*/
		for(int i = 30; i >= 17; i--) {
			expectedPath.add(new Coordinate(i,16));
		}
		
		/* Row 16 Columns 5 - 16 */
		for(int i = 16; i >= 5; i--) {
			expectedPath.add(new Coordinate(16,i));
		}
		
		/***********************************************************/
		ArrayList<Coordinate> actual = bfs.calculate(graph1, src1, dest1);
		
		boolean eContainsA = expectedPath.containsAll(actual);
		boolean aContainsE= actual.containsAll(expectedPath);
		
		assertEquals(eContainsA,aContainsE, 
				"Expected and actual are strict subsets of each other thus equal");
		
		/*
		 * print arrayLists
		 * 
		System.out.println(" expected: ");
		
		// print contents
		for(int i = 0; i < expectedPath1.size(); i++) {
			System.out.print( "(" + expectedPath1.get(i).x + "," );
			System.out.print( expectedPath1.get(i).y + ") " );
		}
		System.out.println(" ");
		System.out.println(" actual: ");
		// print contents
		for(int i = 0; i < actual.size(); i++) {
			System.out.print( "(" + actual.get(i).x + ",");
			System.out.print( actual.get(i).y + ") ");
		}
		 */
	}
	
	@Test
	void calculateEmptyGraph() {
		
		Coordinate src1 = new Coordinate(16,5);
		Coordinate dest1 = new Coordinate(30,16);
		
		ArrayList<Coordinate> expected = new ArrayList<Coordinate>();  // empty
		ArrayList<Coordinate> actual = bfs.calculate(graph2, src1, dest1);
		
		assertEquals(expected,actual, "Should be both empty");
	}
	
}

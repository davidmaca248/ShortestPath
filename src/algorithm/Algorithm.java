package algorithm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import utils.Coordinate;
import utils.Graph;

/**
* Name   : Algorithm
* Purpose: Algorithm Class used in the ShortestPath program
* Details: An abstract class and JPanel that represents a shortest path algorithm.
* 		   Extended by shortest path algorithm implementations.
* 		   Contains a QueueNode class
* 		   Contains a method calculate() which represents the algorithm to be implemented.
*
* @author  David Macababayao
* @version 1.0
* @since   2021-05-10 
*/
public abstract class Algorithm extends JPanel implements ActionListener{
	private final JPanel graphPanel, botPanel;
	private final JButton nodes[][], resetBtn,midBtn, calcBtn;
	private final JLabel msgLabel;
	
	private final Timer timer;
	
	private final int FRAME_WIDTH = 700, FRAME_HEIGHT = 700, BOT_HEIGHT = 70, BOT_BTN_WIDTH = 180, GRAPH_SIZE = 50;
	private int mode; /* 1 = wallmode, 2 = srcMode, 3 = destMode, 4 = stageMode, 5 = calcMode , 6 = displayMode*/
	
	protected Graph graph;
	protected ArrayList<Coordinate> walls;
	protected ArrayList<Coordinate> path;
	protected boolean [][]visited;
	protected Coordinate src, dst;

	/* Used to get the 4 neighbors of a node (ONLY ADJACENT NODES) */
	protected static int rowNum[] = {-1, 0, 0, 1};
	protected static int colNum[] = {0, -1, 1, 0};
	
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
	
	Algorithm(){
		mode = 1;
		graph = new Graph(GRAPH_SIZE);
		path = new ArrayList<Coordinate>();
		walls = new ArrayList<Coordinate>();
		visited = new boolean[GRAPH_SIZE][GRAPH_SIZE];
		src = new Coordinate();
		dst = new Coordinate();
		
		/* set the delay for each actionPerformed() call */
		timer = new Timer(10,this);
		timer.start();
		
		/* graph Panel */
		graphPanel = new JPanel();
		graphPanel.setLayout(new GridLayout(GRAPH_SIZE,GRAPH_SIZE,-1,-1));
		graphPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		graphPanel.setBackground(Color.blue);
		
		/* graph (buttons) */
		nodes = new JButton[GRAPH_SIZE][GRAPH_SIZE];
		for (int r = 0; r < GRAPH_SIZE; r++) {
			for (int c = 0; c < GRAPH_SIZE; c++) {
				nodes[r][c] = new JButton();
				nodes[r][c].setFocusable(false);
				nodes[r][c].addActionListener(this);
				
				/* Fills up the GRAPH_SIZE x GRAPH_SIZE grid with buttons */
				graphPanel.add(nodes[r][c]);

			}
		}
		
		/* bot Panel */		
		msgLabel = new JLabel();
		msgLabel.setText("Choose Starting Point");
		msgLabel.setForeground(Color.black);
		msgLabel.setBackground(Color.cyan);
		msgLabel.setOpaque(true);
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		msgLabel.setFont(new Font("Arial",Font.PLAIN,25));

		resetBtn = new JButton();
		resetBtn.setText("Reset");
		resetBtn.setFocusable(false);
		resetBtn.setPreferredSize(new Dimension(BOT_BTN_WIDTH,0));
		resetBtn.setFont(new Font("Arial",Font.BOLD,20));
		resetBtn.setForeground(Color.black);
		resetBtn.setBackground(Color.red);
		resetBtn.addActionListener(this);
		
		midBtn = new JButton();
		midBtn.setText("Next");
		midBtn.setFocusable(false);
		midBtn.setPreferredSize(new Dimension(BOT_BTN_WIDTH,0));
		midBtn.setFont(new Font("Arial",Font.BOLD,20));
		midBtn.setForeground(Color.black);
		midBtn.setBackground(Color.orange);
		midBtn.addActionListener(this);
		
		calcBtn = new JButton();
		calcBtn.setText("Calculate");
		calcBtn.setFocusable(false);
		calcBtn.setFont(new Font("Ink Free",Font.BOLD,30));
		calcBtn.setForeground(Color.black);
		calcBtn.setBackground(Color.pink);		
		calcBtn.addActionListener(this);
		
		botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		botPanel.setPreferredSize(new Dimension(FRAME_WIDTH,BOT_HEIGHT));
		botPanel.add(msgLabel,BorderLayout.CENTER);
		botPanel.add(resetBtn,BorderLayout.WEST);
		botPanel.add(midBtn,BorderLayout.EAST);

		
		resetGraph(); /* colors the grid */
		
		this.setLayout(new BorderLayout());
		this.add(graphPanel,BorderLayout.NORTH);
		this.add(botPanel,BorderLayout.SOUTH);
	}
	
	/*
	 * Name   : calculate
	 * Purpose: Calculates the shortest path from a source node to a destination node in the given graph
	 * Details: Overridden by shortest path algorithm classes.
	 * 	        Fills in the visited and path attributes.
	 * @return: void
	 */
	public void calculate() throws InterruptedException{
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final Object srcBtn = e.getSource();

		switch(mode) {
			case 1:{ /* wall mode */
				if(srcBtn == resetBtn) {
					resetGraph();
				}
				else if(srcBtn == midBtn) {
					/* go to srcMode */
					mode = 2;
					msgLabel.setText("Choose Starting Point");
					midBtn.setText("Return");
				}
				else if(srcBtn == calcBtn) {
					
				}
				else {  /* node clicked */
					/* add walls to screen */
					for (int r = 0; r < GRAPH_SIZE; r++) {
						for (int c = 0; c < GRAPH_SIZE; c++) {
							if(srcBtn == nodes[r][c]) {
								
								/* if not a wall */
								if(graph.getValue(new Coordinate(r,c)) == 0){
									nodes[r][c].setBackground(Color.BLACK);
									walls.add(new Coordinate(r, c));
								} 
								else { // already a wall
									nodes[r][c].setBackground(Color.green);
									
									/* remove from walls*/
									for(int i = 0; i < walls.size(); i++) {
										if(walls.get(i).x == r && walls.get(i).y == c) {
											walls.remove(i);
										}
									}
								}
								
								/* apply walls or toggle to the graph */
								graph.flip(new Coordinate(r,c));
							}
						}
					}
				}
				break;
			}
			case 2:{ /* srcMode */
				if(srcBtn == resetBtn) {
					resetGraph();
				}
				else if(srcBtn == midBtn) {
					/* go to wallMode */					
					mode = 1;
					msgLabel.setText("Select Walls");
					midBtn.setText("Next");
				}
				else if(srcBtn == calcBtn) {
					
				}
				else {  /* node clicked */
					/* set startNode */
					for (int r = 0; r < GRAPH_SIZE; r++) {
						for (int c = 0; c < GRAPH_SIZE; c++) {
							if(srcBtn == nodes[r][c]) {
								
								/* if not a wall */
								if(graph.getValue(new Coordinate(r,c)) == 0){
									src.x = r;
									src.y = c;
									
									nodes[r][c].setBackground(Color.yellow);
									mode = 3;  // go to endMode
									msgLabel.setText("Choose End Point");
								} else {
									msgLabel.setText("Invalid point, choose again");
									return;
								}
									
							}
						}
					}
				}
				break;
			}
			case 3:{ /* destMode */
				if(srcBtn == resetBtn) {
					resetGraph();
				}
				else if(srcBtn == midBtn) {
					/* go to srcMode */
					mode = 2;
					msgLabel.setText("Choose Starting Point");
					midBtn.setText("Return");

					/* reset src */
					nodes[src.x][src.y].setBackground(Color.green);
					src.x = -1;
					src.y = -1;
				}
				else if(srcBtn == calcBtn) {
					
				}
				else {  /* node clicked */
					/* set destNode */
					for (int r = 0; r < GRAPH_SIZE; r++) {
						for (int c = 0; c < GRAPH_SIZE; c++) {
							if(srcBtn == nodes[r][c]) {
								/* if not a wall */
								if(graph.getValue(new Coordinate(r,c)) == 0){
									dst.x = r;
									dst.y = c;
									nodes[r][c].setBackground(Color.magenta);
									mode = 4;  /* go to stageMode */
									
									/* Calculate Button appears */
									botPanel.remove(msgLabel);
									botPanel.add(calcBtn,BorderLayout.CENTER);
								} else {
									msgLabel.setText("Invalid point, choose again");
									return;
								}	
							}
						}
					}
				}
				break;
			}
			case 4:{ /* stageMode */
				if(srcBtn == resetBtn) {
					resetGraph();
				}
				else if(srcBtn == midBtn) {
					/* go to destMode */ 					
					mode = 3;
					msgLabel.setText("Choose End Point");
					
					/* remove calcBtn */
					botPanel.remove(calcBtn);
					botPanel.add(msgLabel,BorderLayout.CENTER);
					
					/* reset dst */
					nodes[dst.x][dst.y].setBackground(Color.green);
					dst.x = -1;
					dst.y = -1;
				}
				else if(srcBtn == calcBtn){  /* calculate */
					mode = 5;
					
					msgLabel.setText("Calculating...");
					
					/* remove calcBtn */
					botPanel.remove(calcBtn);
					botPanel.add(msgLabel,BorderLayout.CENTER);
					
					/* run calculate on a separate thread */
					Thread t1 = new Thread(new Runnable() {
					    @Override
					    public void run() {
					        try {
								calculate();
								mode = 6;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					    }
					});  
					t1.start();
					
					mode = 5;
				}
				else {}
				break;
			}
			case 5:{ /* calcMode */
				drawVisited();
				break;
			}
			case 6:{ /* displayMode */
				if(path.isEmpty()) {
					msgLabel.setText("There is no path found");
				} else {
					msgLabel.setText("Displaying Shortest Path");
				    drawPath();
				}

			    /* keep src and dst colors */
			    nodes[src.x][src.y].setBackground(Color.yellow);
			    nodes[dst.x][dst.y].setBackground(Color.magenta);

				/* remove calcBtn */
				botPanel.remove(calcBtn);
				botPanel.add(msgLabel,BorderLayout.CENTER);
			    
			    mode = 7;
			    break;
			}
			case 7:{
				if(srcBtn == resetBtn) {
					resetGraph();
				}
				else if(srcBtn == midBtn) {
					/* go to dstMode */
					mode = 3;
					msgLabel.setText("Choose End Point");

					/* reset dst */
					nodes[dst.x][dst.y].setBackground(Color.green);
					dst.x = -1;
					dst.y = -1;
					
					remPath();
					remVisited();
					
					nodes[src.x][src.y].setBackground(Color.yellow);
				}
				else{} /* node clicked */	
	
				break;
			}
			default:{
				break;
			}
		}
		this.revalidate();
		this.repaint();
	}

	/*
	 * Name   : resetGraph
	 * Purpose: Resets this panel to constructor specifications.
	 * Details: Clears the walls, path, visited attributes and colors the graph buttons to original.
	 * 
	 * @return: void
	 */
	public void resetGraph() {	
		msgLabel.setText("Select Walls");
		midBtn.setText("Next");
		
		if(mode == 4) {
			botPanel.remove(calcBtn);
			botPanel.add(msgLabel,BorderLayout.CENTER);
		}
		
		mode = 1;
		graph = new Graph(GRAPH_SIZE);
		path = new ArrayList<Coordinate>();
		walls = new ArrayList<Coordinate>();
		visited = new boolean[GRAPH_SIZE][GRAPH_SIZE];
		src = new Coordinate();
		dst = new Coordinate();
		
		/* reset graph */
		for (int r = 0; r < GRAPH_SIZE; r++) {
			for (int c = 0; c < GRAPH_SIZE; c++) {					
				/* paths(black) and borders(green) */
				if(r == 0 || r == GRAPH_SIZE-1 || c == 0 || c == GRAPH_SIZE-1) {  // original walls
					nodes[r][c].setBackground(Color.black);
					nodes[r][c].setEnabled(false);
					walls.add(new Coordinate(r,c));
				} else {
					nodes[r][c].setBackground(Color.green);
				}
			}
		}
		graph.setWalls(walls);
	}
	
	/*
	 * Name   : drawVisited
	 * Purpose: Colors the graph buttons on visited coordinates
	 * Details: Colors the visited graph nodes pink.
	 * 
	 * @return: void
	 */
	public void drawVisited() {
		for (int r = 0; r < GRAPH_SIZE; r++) {
			for (int c = 0; c < GRAPH_SIZE; c++) {					
				if(visited[r][c]) {  // original walls
					nodes[r][c].setBackground(Color.pink);
				} 
			}
		}
		/* keep start node yellow */
		nodes[src.x][src.y].setBackground(Color.yellow);
		/* keep start node yellow */
		nodes[dst.x][dst.y].setBackground(Color.magenta);
	}
	
	/*
	 * Name   : remVisited
	 * Purpose: Resets the graph buttons on visited coordinates
	 * Details: Colors the visited graph nodes green and re-instantiate visited.
	 * 
	 * @return: void
	 */
	public void remVisited() {
		for (int r = 0; r < GRAPH_SIZE; r++) {
			for (int c = 0; c < GRAPH_SIZE; c++) {					
				//paths (black) and borders(green)
				if(visited[r][c]) {  // original walls
					nodes[r][c].setBackground(Color.green);
				} 
			}
		}
		visited = new boolean[GRAPH_SIZE][GRAPH_SIZE];
	}
	
	/*
	 * Name   : drawPath
	 * Purpose: Colors the graph buttons on path coordinates
	 * Details: Colors the path graph nodes red.
	 * 
	 * @return: void
	 */
	public void drawPath() {
		for (int i = 0; i < path.size(); i++) {
			nodes[path.get(i).x][path.get(i).y].setBackground(Color.red);
		}
	}
	
	/*
	 * Name   : remPath
	 * Purpose: Resets the graph buttons on path coordinates
	 * Details: Colors the path graph nodes green and clears path.
	 * 
	 * @return: void
	 */
	public void remPath() {
		for (int i = 0; i < path.size(); i++) {
			nodes[path.get(i).x][path.get(i).y].setBackground(Color.green);
		}
		path.clear();
	}
}

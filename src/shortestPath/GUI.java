package shortestPath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithm.Algorithm;
import algorithm.BFS;
import utils.Coordinate;
import utils.Graph;

/**
* Name   : GUI
* Purpose: JFrame that is displayed on the GUI of the ShortestPath program
* Details: Contains the content on the GUI
*
* @author  David Macababayao
* @version 1.0
* @since   2021-05-10 
*/
public class GUI extends JFrame implements ActionListener{
	
	private final Algorithm algo;
	private final int graphSize = 50;
	private final Graph graph;
	private ArrayList<Coordinate> walls;
	private ArrayList<Coordinate> path;
	private Coordinate start;
	private Coordinate end;
	private int mode;           /* 0 = wallMode, 1= startMode, 2 = endMode 3 = doneMode */
	
	private final JButton nodes[][], startBtn, midBtn, resetBtn;
	private final JPanel mainPanel, startPanel, programPanel, graphPanel, botPanel; 
	private final JLabel msgLabel, titleLabel;
	private final String modeMsg[], midBtnMsg[];

	
	/* Sets up the GUI contents and the program variables*/
	public GUI() {
		
		/* Program Variables */
		algo = new BFS();
		graph = new Graph(graphSize);
		walls = new ArrayList<Coordinate>();
		path = new ArrayList<Coordinate>();
		start = new Coordinate();
		end = new Coordinate();
		mode = 0;
		modeMsg = new String[4];
		modeMsg[0] = "Select Walls";
		modeMsg[1] = "Select Starting Point";
		modeMsg[2] = "Select End Point";
		modeMsg[3] = "Displaying Shortest Path";
		midBtnMsg = new String[2];
		midBtnMsg[0] = "Walls Selected";
		midBtnMsg[1] = "Return";
		
		/* GUI */
		titleLabel = new JLabel();
		titleLabel.setText("Shortest Path Calculator");
		titleLabel.setForeground(Color.cyan);
		titleLabel.setBackground(Color.black);
		titleLabel.setOpaque(true);
		titleLabel.setPreferredSize(new Dimension(0,80));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial",Font.BOLD,40));

		mainPanel = new JPanel();
		/* So that startPanel would occupy the whole otherPanel */
		mainPanel.setLayout(new BorderLayout());
		
		startPanel = new JPanel();
		startPanel.setBackground(Color.orange);
		/* So that buttons would have a 200 gap between them */
		startPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,200));
		
		startBtn = new JButton();
		startBtn.setText("Click here to start");
		startBtn.setFocusable(false);
		startBtn.setPreferredSize(new Dimension(400,100));
		startBtn.setFont(new Font("Serif",Font.BOLD,30));
		startBtn.setForeground(Color.black);
		startBtn.setBackground(Color.cyan);
		startBtn.addActionListener(this);
		
		startPanel.add(startBtn);
		
		programPanel = new JPanel();
		programPanel.setLayout(new BorderLayout());
		
		graphPanel = new JPanel();
		graphPanel.setLayout(new GridLayout(graphSize,graphSize,0,0));
		graphPanel.setBackground(Color.blue);
		
		/* graph (buttons) */
		nodes = new JButton[graphSize][graphSize];
		for (int r = 0; r < graphSize; r++) {
			for (int c = 0; c < graphSize; c++) {
				nodes[r][c] = new JButton();
				nodes[r][c].setFocusable(false);
				nodes[r][c].addActionListener(this);
				
				/* Fills up the graphSize x graphSize grid with buttons */
				graphPanel.add(nodes[r][c]);

				/* initialize original walls (green) and paths (black) */
				if(r == 0 || r == graphSize-1 || c == 0 || c == graphSize-1) { 
					nodes[r][c].setBackground(Color.black);
				} else {
					nodes[r][c].setBackground(Color.green);
				}
			}
		}
		
		botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		botPanel.setPreferredSize(new Dimension(0,60));
		
		msgLabel = new JLabel();
		msgLabel.setText(modeMsg[0]);
		msgLabel.setForeground(Color.black);
		msgLabel.setBackground(Color.cyan);
		msgLabel.setOpaque(true);
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		msgLabel.setFont(new Font("Arial",Font.PLAIN,30));

		midBtn = new JButton();
		midBtn.setText("Walls Selected");
		midBtn.setFocusable(false);
		midBtn.setPreferredSize(new Dimension(200,0));
		midBtn.setFont(new Font("Arial",Font.BOLD,20));
		midBtn.setForeground(Color.black);
		midBtn.setBackground(Color.orange);
		midBtn.addActionListener(this);
		
		resetBtn = new JButton();
		resetBtn.setText("Reset");
		resetBtn.setFocusable(false);
		resetBtn.setPreferredSize(new Dimension(200,0));
		resetBtn.setFont(new Font("Arial",Font.BOLD,20));
		resetBtn.setForeground(Color.black);
		resetBtn.setBackground(Color.red);
		resetBtn.addActionListener(this);
		
		botPanel.add(msgLabel,BorderLayout.CENTER);
		botPanel.add(midBtn,BorderLayout.WEST);
		botPanel.add(resetBtn,BorderLayout.EAST);
		
		/* programPanel will contain graphPanel and botPanel */
		programPanel.add(graphPanel);
		programPanel.add(botPanel,BorderLayout.SOUTH);

	}

	/* initializes the GUI */
	public void initialize() {
		
		/* frame*/
		this.setTitle("ShortestPath");
		this.setResizable(false);
		this.setSize(800, 900);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.orange);
		
		this.add(titleLabel,BorderLayout.NORTH);
		this.add(mainPanel);
		
		/* mainPanel will INITIALLY contain the startPanel */
		mainPanel.add(startPanel);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		final Object srcBtn = e.getSource();
		
		if(srcBtn==startBtn) {
			mainPanel.remove(startPanel);
			mainPanel.add(programPanel);
			this.validate(); /* Refreshes the frame */
		} 
		else if(srcBtn == resetBtn) {
			mode = 0;
			msgLabel.setText(modeMsg[mode]);
			midBtn.setText(midBtnMsg[0]);
			
			walls.clear();
			path.clear();
			
			// reset graph
			graph.resetGraph();
			for (int r = 0; r < graphSize; r++) {
				for (int c = 0; c < graphSize; c++) {					
					// paths (black) and borders(green)
					if(r == 0 || r == graphSize-1 || c == 0 || c == graphSize-1) {  // original walls
						nodes[r][c].setBackground(Color.black);
					} else {
						nodes[r][c].setBackground(Color.green);
					}
				}
			}
			
			return;
		}
		else if (srcBtn == midBtn) {
			switch(mode) {
				case 0: { // wallMode
					mode = 1;       // go to start mode
					msgLabel.setText(modeMsg[mode]);
					midBtn.setText(midBtnMsg[1]);

					//apply walls to the graph
					graph.setWalls(walls);
					break;
				}
				case 1: { // startMode
					mode = 0;
					msgLabel.setText(modeMsg[mode]);
					midBtn.setText(midBtnMsg[0]);
					break;
				}
				case 2: { // endMode
					mode = 1;  // back to start mode
					msgLabel.setText(modeMsg[mode]);
					
					// reset start
					nodes[start.x][start.y].setBackground(Color.GREEN);
					start.x = -1;
					start.y = -1;
					
					break;
				}
				case 3: { // doneMode
					mode = 2;  // back to end mode
					msgLabel.setText(modeMsg[mode]);
					
					// reset end
					nodes[end.x][end.y].setBackground(Color.GREEN);  // incase path is empty
					end.x = -1;
					end.y = -1;
					
					// remove path
					for (Coordinate coor: path) { 
						nodes[coor.x][coor.y].setBackground(Color.GREEN); 
					} 
					nodes[start.x][start.y].setBackground(Color.YELLOW);    // Reset end node (since its not in path)
					
					path.clear();
					break;
				}
			}
		} 
		else {  // graph nodes clicked
			switch(mode) {
				case 0: { // wallMode
					// add walls to screen
					for (int r = 0; r < graphSize; r++) {
						for (int c = 0; c < graphSize; c++) {
							if(srcBtn == nodes[r][c]) {
								nodes[r][c].setBackground(Color.BLACK);
								walls.add(new Coordinate(r, c));
							}
						}
					}
					break;
				}
				case 1: { // startMode
					// set startNode
					for (int r = 0; r < graphSize; r++) {
						for (int c = 0; c < graphSize; c++) {
							if(srcBtn == nodes[r][c]) {
								
								// check if valid start node (not a wall)
								if(graph.getValue(new Coordinate(r,c)) == 1) {
									start.x = r;
									start.y = c;
									nodes[r][c].setBackground(Color.YELLOW);
									mode = 2;  // go to endMode
									msgLabel.setText(modeMsg[mode]);
								} else {
									msgLabel.setText("Invalid starting point");
									return;
								}	
							}
						}
					}
					break;
				}
				case 2: { // endMode
					// set endNode
					for (int r = 0; r < graphSize; r++) {
						for (int c = 0; c < graphSize; c++) {
							if(srcBtn == nodes[r][c]) {
								
								// check if valid end node (not a wall)
								if(graph.getValue(new Coordinate(r,c)) == 1) {
									end.x = r;
									end.y = c;
									nodes[r][c].setBackground(Color.MAGENTA);
									mode = 3; // change mode to doneMode
									
									// calculate path
									path = algo.calculate(graph,start,end);
									
									if(path.isEmpty()) {
										msgLabel.setText("No path from src to dest");
									} else {
										msgLabel.setText(modeMsg[3]);
										
										//color all nodes in the path 
										for (Coordinate coor : path) { 
										  nodes[coor.x][coor.y].setBackground(Color.RED);
										} 
										
										// keep start node yellow 
										nodes[start.x][start.y].setBackground(Color.YELLOW);  
										// keep end node magenta 
										nodes[end.x][end.y].setBackground(Color.MAGENTA);   
										
									}
								} else {
									msgLabel.setText("Invalid end point");
									return;
								}	
							}
						}
					}
					break;
				}
				case 3: {  // doneMode
					break; // do nothing
				}
				default: {  
					break;
				}
			}
		}
	}
}

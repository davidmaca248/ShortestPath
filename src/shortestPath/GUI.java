package shortestPath;

import java.awt.BorderLayout;
import java.awt.Color;
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
	private final int graphSize= 50;
	private final Graph graph;
	private ArrayList<Coordinate> walls;
	private ArrayList<Coordinate> path;
	private Coordinate start;
	private Coordinate end;
	private int mode;           /* 0 = wallMode, 1= startMode, 2 = endMode 3 = doneMode */
	
	private JButton nodes[][], returnBtn, resetBtn;
	private JPanel titlePanel, graphPanel, botPanel, msgPanel, returnPanel, resetPanel; 
	private JLabel msg, title;
	private final String modeMsg[], wallBtn = "Walls Selected";
	
	public GUI() {
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
		
		/* GUI */
		this.setTitle("ShortestPath");
		this.setResizable(false);
		this.setSize(800, 850);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		title = new JLabel("Shortest Path Calculator");
		title.setFont(new Font("Arial", Font.BOLD, 43));
		title.setBackground(Color.black);
		title.setForeground(Color.cyan);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setOpaque(true);
		
		msg = new JLabel(modeMsg[0]);
		msg.setFont(new Font("Arial", Font.PLAIN, 20));
		msg.setBackground(Color.black);
		msg.setForeground(Color.red);
		msg.setOpaque(true);
		
		/* panels */
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		
		graphPanel = new JPanel(new GridLayout(graphSize, graphSize));
		graphPanel.setBackground(Color.BLUE);
		
		botPanel = new JPanel();
		botPanel.setLayout(new GridLayout(1, 1));
		
		msgPanel = new JPanel();
		msgPanel.setBackground(Color.BLACK);
		returnPanel = new JPanel();
		returnPanel.setBackground(Color.BLACK);
		resetPanel = new JPanel();
		resetPanel.setBackground(Color.BLACK);
		
		/* buttons */
		returnBtn = new JButton(wallBtn);    // initial title
		returnBtn.addActionListener(this);
		
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(this);
		
		/* Color the graph (buttons) */
		nodes = new JButton[graphSize][graphSize];
		for (int r = 0; r < graphSize; r++) {
			for (int c = 0; c < graphSize; c++) {
				nodes[r][c] = new JButton();
				graphPanel.add(nodes[r][c]);
				nodes[r][c].setFocusable(false);
				
				/* initialize original walls (green) and paths (black)*/
				if(r == 0 || r == graphSize-1 || c == 0 || c == graphSize-1) { 
					nodes[r][c].setBackground(Color.black);
				} else {
					nodes[r][c].setBackground(Color.green);
					nodes[r][c].addActionListener(this);
				}
			}
		}
		
		/* add components */
		titlePanel.add(title);
		msgPanel.add(msg);
		returnPanel.add(returnBtn);
		resetPanel.add(resetBtn);
		
		botPanel.add(msgPanel);
		botPanel.add(returnPanel);
		botPanel.add(resetPanel);

		this.add(titlePanel, BorderLayout.NORTH);
		this.add(graphPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		this.revalidate();                        /* used so that frame appears on run time*/
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		final Object srcBtn = e.getSource();
		
		if(srcBtn == resetBtn) {
			mode = 0;
			msg.setText(modeMsg[mode]);
			returnBtn.setText(wallBtn);
			
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
		else if (srcBtn == returnBtn) {
			switch(mode) {
				case 0: { // wallMode
					mode = 1;       // go to start mode
					msg.setText(modeMsg[mode]);
					returnBtn.setText("Return");

					//apply walls to the graph
					graph.setWalls(walls);
					break;
				}
				case 1: { // startMode
					mode = 0;
					msg.setText(modeMsg[mode]);
					returnBtn.setText(wallBtn);
					break;
				}
				case 2: { // endMode
					mode = 1;  // back to start mode
					msg.setText(modeMsg[mode]);
					
					// reset start
					nodes[start.x][start.y].setBackground(Color.GREEN);
					start.x = -1;
					start.y = -1;
					
					break;
				}
				case 3: { // doneMode
					mode = 2;  // back to end mode
					msg.setText(modeMsg[mode]);
					
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
									msg.setText(modeMsg[mode]);
								} else {
									msg.setText("Invalid starting point");
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
										msg.setText("No path from src to dest");
									} else {
										msg.setText(modeMsg[3]);
										
										//color all nodes in the path 
										for (Coordinate coor : path) { 
										  nodes[coor.x][coor.y].setBackground(Color.RED);
										} 
										
										/* keep start node yellow */
										nodes[start.x][start.y].setBackground(Color.YELLOW);  
										/* keep end node magenta */
										nodes[end.x][end.y].setBackground(Color.MAGENTA);   
										
									}
								} else {
									msg.setText("Invalid end point");
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

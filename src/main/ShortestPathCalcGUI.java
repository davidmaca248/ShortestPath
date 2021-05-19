package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithm.Algorithm;
import algorithm.BFS;

/**
 * Name : ShortestPathCalcGUI Purpose: JFrame that is displayed on the GUI of
 * the ShortestPath program Details: Contains the content on the GUI
 *
 * @author David Macababayao
 * @version 1.0
 * @since 2021-05-10
 */
public class ShortestPathCalcGUI extends JFrame implements ActionListener {

	private final int FRAME_SIZE = 700;
	private int mode; /* 0 = startMode, 1 = chooseMode, 2 = runMode */
	private final JPanel startPanel, choosePanel, chooseBtnPanel;
	private final JLabel startLabel, chooseLabel;
	private final JButton startBtn, bfsBtn, dfsBtn;
	private Algorithm algo;

	public ShortestPathCalcGUI() {
		mode = 0;

		/* Start Panel */
		startPanel = new JPanel();
		startPanel.setLayout(new BorderLayout());

		startLabel = new JLabel();
		startLabel.setText("Shortest Path Calculator");
		startLabel.setForeground(Color.cyan);
		startLabel.setBackground(Color.blue);
		startLabel.setOpaque(true);
		startLabel.setPreferredSize(new Dimension(FRAME_SIZE, 570));
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		startLabel.setFont(new Font("Arial", Font.BOLD, 50));

		startBtn = new JButton();
		startBtn.setText("Click here to start");
		startBtn.setFocusable(false);
		startBtn.setPreferredSize(new Dimension(FRAME_SIZE, 200));
		startBtn.setFont(new Font("Serif", Font.BOLD, 30));
		startBtn.setForeground(Color.black);
		startBtn.setBackground(Color.cyan);
		startBtn.addActionListener(this);

		startPanel.add(startLabel, BorderLayout.NORTH);
		startPanel.add(startBtn, BorderLayout.SOUTH);

		/* Choose Panel */
		choosePanel = new JPanel();
		choosePanel.setLayout(new BorderLayout());

		chooseLabel = new JLabel();
		chooseLabel.setText("Choose Shortest Path Algorithm");
		chooseLabel.setForeground(Color.red);
		chooseLabel.setBackground(Color.darkGray);
		chooseLabel.setOpaque(true);
		chooseLabel.setPreferredSize(new Dimension(FRAME_SIZE, 420));
		chooseLabel.setHorizontalAlignment(JLabel.CENTER);
		chooseLabel.setFont(new Font("Arial", Font.BOLD, 40));

		chooseBtnPanel = new JPanel();
		chooseBtnPanel.setBackground(Color.orange);
		chooseBtnPanel.setPreferredSize(new Dimension(FRAME_SIZE, 350));
		chooseBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 75));

		bfsBtn = new JButton();
		bfsBtn.setText("BFS");
		bfsBtn.setFocusable(false);
		bfsBtn.setPreferredSize(new Dimension(FRAME_SIZE / 3, 200));
		bfsBtn.setFont(new Font("Serif", Font.BOLD, 30));
		bfsBtn.setForeground(Color.black);
		bfsBtn.setBackground(Color.green);
		bfsBtn.addActionListener(this);

		dfsBtn = new JButton();
		dfsBtn.setText("DFS");
		dfsBtn.setFocusable(false);
		dfsBtn.setPreferredSize(new Dimension(FRAME_SIZE / 3, 200));
		dfsBtn.setFont(new Font("Serif", Font.BOLD, 30));
		dfsBtn.setForeground(Color.black);
		dfsBtn.setBackground(Color.green);
		dfsBtn.addActionListener(this);
		dfsBtn.setEnabled(false); /* disabled */

		chooseBtnPanel.add(bfsBtn);
		chooseBtnPanel.add(dfsBtn);

		choosePanel.add(chooseLabel, BorderLayout.NORTH);
		choosePanel.add(chooseBtnPanel, BorderLayout.SOUTH);

		/* Frame, initialize GUI */
		this.setTitle("Shortest Path Calculator");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.orange);
		this.setLocationRelativeTo(null);

		this.add(startPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		final Object srcBtn = e.getSource();

		switch (mode) {
			case 0: {
				/* switch to modePanel */
				if (srcBtn == startBtn) {
					mode = 1;
					this.remove(startPanel);
					this.add(choosePanel);
				}
				break;
			}
			case 1: {
				if (srcBtn == bfsBtn) {
					algo = new BFS();
				} else if (srcBtn == dfsBtn) {
					// algo = new DFS();
				} else {
					break;
				}

				/* switch to sizePanel */
				mode = 2;
				this.remove(choosePanel);
				this.add(algo);
				break;
			}
			case 2: {
				break;
			}
			default: {
				break;
			}
		}

		this.revalidate(); /* Refreshes the frame */
		this.repaint();
	}
}

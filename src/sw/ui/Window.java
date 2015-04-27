package sw.ui;

import java.io.*;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//import ui.NetworkDisplay;
import sw.Configuration;
import sw.graph.Graph;
import sw.SIRSimulation;

public class Window extends JFrame {
	public static final long serialVersionUID = 41L;
	protected SIRSimulation simulation;
	protected NetworkDisplay networkDisplay;
	JButton playButton;
	protected Thread thread;
	protected Graph graph;
	double alpha;
	double gamma;
	public Window(Configuration conf) {
		super("Small World Network Simulator");


		final int defaultNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "default"));

		graph = new Graph(defaultNumNodes);
		graph.makeRegular();
		networkDisplay = new NetworkDisplay(conf, graph);
		getContentPane().add(networkDisplay, BorderLayout.CENTER);

		JPanel settingsPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS);
		settingsPanel.setLayout(boxLayout);

		GraphSettingsPanel graphSettingsPanel = new GraphSettingsPanel(conf,graph,networkDisplay);
		settingsPanel.add(graphSettingsPanel);

		SIRSettingsPanel sirSettingsPanel = new SIRSettingsPanel(conf,this);
		settingsPanel.add(sirSettingsPanel);

		simulation = new SIRSimulation(graph, 1000, alpha, gamma, this);

		JPanel buttonGroup = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(buttonGroup, BoxLayout.LINE_AXIS);
		buttonGroup.setLayout(boxLayout2);

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(simulation.isRunning()) {
					try {
						simulation.terminate();
						thread.join();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}

				simulation.reset();
				networkDisplay.repaint();
				playButton.setText("Play");
			}
		});

		playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(simulation.isRunning()) {
					try {
						simulation.terminate();
						thread.join();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}

					playButton.setText("Play");
				} else {
					thread = new Thread(simulation);

					simulation.setAlpha(alpha);
					simulation.setGamma(gamma);
					thread.start();
					
					playButton.setText("Pause");
				}
			}
		});
		JButton forwardButton = new JButton("Jump to End");
		buttonGroup.add(resetButton);
		buttonGroup.add(playButton);
		buttonGroup.add(forwardButton);
		settingsPanel.add(buttonGroup);

		getContentPane().add(settingsPanel, BorderLayout.LINE_START);

		int windowWidth = 800;
		int windowHeight = 600;
		setBounds(50,100,windowWidth,windowHeight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void onTimeStep() {
		networkDisplay.repaint();
	}

	public void onSimulationEnd() {
		playButton.setText("Play");
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
}

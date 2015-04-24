package sw.ui;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

import ui.NetworkDisplay;
import sw.Configuration;
import sw.graph.Graph;

public class Window extends JFrame {
	public static final long serialVersionUID = 41L;
	protected Graph graph;
	public Window(Configuration conf) {
		super("Small World Network Simulator");
		getContentPane().add(new NetworkDisplay(), BorderLayout.CENTER);

		JPanel settingsPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS);
		settingsPanel.setLayout(boxLayout);

		JLabel nodeSliderLabel = new JLabel("Number of nodes", JLabel.CENTER);
		nodeSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(nodeSliderLabel);

		final int minNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "min"));
		final int maxNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "max"));
		final int defaultNumNodes = Integer.parseInt(conf.getPropertyValue("numNodes", "default"));
		JSlider nodeSlider = new JSlider(JSlider.HORIZONTAL, minNumNodes, maxNumNodes, defaultNumNodes);
		nodeSlider.setMinorTickSpacing(Math.abs(maxNumNodes-minNumNodes)/8);
		nodeSlider.setMajorTickSpacing(Math.abs(maxNumNodes-minNumNodes)/4);
		nodeSlider.setPaintTicks(true);
		nodeSlider.setPaintLabels(true);
		settingsPanel.add(nodeSlider);

		graph = new Graph(defaultNumNodes);
		graph.makeRegular();

		getContentPane().add(settingsPanel, BorderLayout.LINE_START);

		int windowWidth = 800;
		int windowHeight = 600;
		setBounds(50,100,windowWidth,windowHeight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

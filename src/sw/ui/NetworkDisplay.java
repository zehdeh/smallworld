package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;

import sw.graph.Graph;
import java.io.*;

public class NetworkDisplay extends JComponent {
	protected Graphics2D graphics2D;
	public static final long serialVersionUID = 42L;
	protected Graph graph;
	protected int r1;
	protected int r2;

	public NetworkDisplay(Graph graph) {
		super();

		this.graph = graph;

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				calculateRadius();
			}
		});

	}

	public void repaint() {
		calculateRadius();

		super.repaint();
	}

	public void calculateRadius() {
		int m = Math.min(getSize().width,getSize().height);
		int padding = (int)(m*0.2);
		r1 = (m-padding)/2;
		r2 = Math.max(2,Math.min(50,(int)((r1*Math.sin(Math.PI/graph.getNumberNodes()))*0.9)));
		System.out.println("r1: " + r1 + ", r2: " + r2);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		graphics2D = (Graphics2D)g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		graphics2D.setColor(Color.black);

		for(int i = 0; i < graph.getNumberNodes(); i++) {
			int x = getNodeX(i);
			int y = getNodeY(i);
				
			graphics2D.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
		}

	}

	public int getNodeX(int index) {
		double t = 2*Math.PI*index/graph.getNumberNodes();
		return (int) Math.round((getWidth()/2) + r1 * Math.cos(t)); 
	}

	public int getNodeY(int index) {
		double t = 2*Math.PI*index/graph.getNumberNodes();
		return (int) Math.round((getHeight()/2) + r1 * Math.sin(t)); 
	}

}

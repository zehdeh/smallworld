package sw.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import java.util.ArrayList;

import sw.graph.*;
import sw.Configuration;
import java.io.*;

public class NetworkDisplay extends JComponent {
	protected Graphics2D graphics2D;
	public static final long serialVersionUID = 42L;
	protected Graph graph;
	protected Configuration conf;
	protected int r1;
	protected int r2;

	public NetworkDisplay(Configuration conf, Graph graph) {
		super();

		this.graph = graph;
		this.conf = conf;

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
		final int maxRadius = Integer.parseInt(conf.getPropertyValue("node", "maxRadius"));
		final int minRadius = Integer.parseInt(conf.getPropertyValue("node", "minRadius"));

		int m = Math.min(getSize().width,getSize().height);
		int padding = (int)(m*0.2);
		r1 = (m-padding)/2;
		r2 = Math.max(2,Math.min(15,(int)((r1*Math.sin(Math.PI/graph.getNumberNodes()))*0.8)));
	}

	protected void drawNodes(Graphics2D graphics2D) {
		Color susceptipleColor = conf.getColorByString(conf.getPropertyValue("node","susceptipleColor"));
		Color infectedColor = conf.getColorByString(conf.getPropertyValue("node","infectedColor"));
		Color recoveredColor = conf.getColorByString(conf.getPropertyValue("node","recoveredColor"));

		for(int i = 0; i < graph.getNumberNodes(); i++) {
			int x = getNodeX(i);
			int y = getNodeY(i);
			InfectionState state = graph.getNodes().get(i).getInfectionState();
			if(state == InfectionState.SUSCEPTIBLE) {
				graphics2D.setColor(susceptipleColor);
			} else if(state == InfectionState.INFECTED) {
				graphics2D.setColor(infectedColor);
			} else {
				graphics2D.setColor(recoveredColor);
			}
				
			graphics2D.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
		}
	}

	protected void drawEdges(Graphics2D graphics2D) {
		Color color = conf.getColorByString(conf.getPropertyValue("edge","color"));
		graphics2D.setColor(color);
		ArrayList<Edge> edges = graph.getEdges();

		for(Edge e : edges) {
			int i1 = graph.getNodeIndex(e.getN1());
			int i2 = graph.getNodeIndex(e.getN2());

			int x1 = getNodeX(i1);
			int y1 = getNodeY(i1);
			int x2 = getNodeX(i2);
			int y2 = getNodeY(i2);

			graphics2D.drawLine(x1,y1,x2,y2);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		graphics2D = (Graphics2D)g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		drawEdges(graphics2D);
		drawNodes(graphics2D);
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

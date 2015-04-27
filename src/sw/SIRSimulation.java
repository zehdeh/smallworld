package sw;

import java.io.*;
import java.lang.Runnable;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

import sw.graph.Graph;
import sw.graph.Node;
import sw.graph.InfectionState;
import sw.ui.NetworkDisplay;

public class SIRSimulation implements Runnable {
	protected Graph graph;
	protected boolean running;
	long timeInterval;
	double alpha;
	double gamma;
	NetworkDisplay networkDisplay;
	public SIRSimulation(Graph graph, long timeInterval, double alpha, double gamma, NetworkDisplay nd) {
		this.graph = graph;
		this.timeInterval = timeInterval;
		this.alpha = alpha;
		this.gamma = gamma;
		this.networkDisplay = nd;
	}
	public void reset() {
		for(Node n : graph.getNodes()) {
			n.setInfectionState(InfectionState.SUSCEPTIBLE);
		}
		networkDisplay.repaint();
	}
	public void terminate() {
		running = false;
	}
	public boolean isRunning() { return running; }
	public void run() {
		running = true;
		ArrayList<Node> susceptibles = new ArrayList<Node>();
		ArrayList<Node> infected = new ArrayList<Node>();
		ArrayList<Node> recovered = new ArrayList<Node>();

		susceptibles.addAll(graph.getNodes());
		Node patientZero = susceptibles.get(randInt(0,susceptibles.size()-1));
		patientZero.setInfectionState(InfectionState.INFECTED);
		infected.add(patientZero);
		networkDisplay.repaint();
		System.out.println("Test");

		try {
			Thread.sleep(timeInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while(running) {
			System.out.println("Still running, alpha: " + alpha + ", gamma: " + gamma);
			if(infected.size() == 0) break;

			ArrayList<Node> newlyInfected = new ArrayList<Node>();
			for(Node n : infected) {
				for(Node neighbor : graph.getNeighbors(n)) {
					if(neighbor.getInfectionState() == InfectionState.SUSCEPTIBLE) {
						if(Math.random() < alpha) {
							neighbor.setInfectionState(InfectionState.INFECTED);

							susceptibles.remove(neighbor);
							newlyInfected.add(neighbor);
						}
					}
				}
			}
			infected.addAll(newlyInfected);

			Iterator<Node> iter = infected.iterator();
			while(iter.hasNext()) {
				Node n = iter.next();
				if(newlyInfected.indexOf(n) != -1) continue;
				if(Math.random() < gamma) {
					n.setInfectionState(InfectionState.RECOVERED);
					iter.remove();
					recovered.add(n);
				}
			}
			
			networkDisplay.repaint();

			try {
				Thread.sleep(timeInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		running = false;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}

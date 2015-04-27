package sw.graph;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
	protected ArrayList<Node> nodes;
	protected ArrayList<Edge> edges;
	protected static final int k = 2;
	public Graph(int numNodes) {
		nodes = new ArrayList<Node>(numNodes);
		for(int i = 0; i < numNodes; i++) {
			nodes.add(new Node());
		}
		edges = new ArrayList<Edge>(numNodes-1);

		makeRegular();
	}

	public final ArrayList<Edge> getEdges() { return edges; }
	public final ArrayList<Node> getNodes() { return nodes; }

	public int getNodeIndex(Node node) {
		return nodes.indexOf(node);
	}

	public void setNumberNodes(int newNo) {
		for(int i = 0; i < Math.abs(newNo - getNumberNodes()); i++) {
			if(newNo > getNumberNodes()) {
				nodes.add(new Node());
			} else {
				nodes.remove(0);
			}
		}

		makeRegular();
	}
	public void rewire(double factor) {
		makeRegular();
		int numEdges = edges.size();

		int newNumEdges = (int)((nodes.size()*k)*(1.0-factor));

		for(int i = 0; i < Math.abs(numEdges-newNumEdges); i++) {
			if(numEdges > newNumEdges) {
				Edge e = getRegularEdge();

				int indexN1 = getNodeIndex(e.getN1());
				int indexN2 = getNodeIndex(e.getN2());
				int randomNodeIndex = indexN1;
				do {
					randomNodeIndex = randInt(0,nodes.size()-1);
				} while(randomNodeIndex == indexN2 && doesEdgeExist(e.getN1(),nodes.get(randomNodeIndex)));

				e.setN2(nodes.get(randomNodeIndex));
			} else {
				Edge e = getIrregularEdge();

				int indexN1 = getNodeIndex(e.getN1());
				int indexN2 = getNodeIndex(e.getN2());
				if(Math.random() < 0.5) {
					int randomNodeIndex = indexN1;
					do {
						randomNodeIndex = randInt(indexN1-k,indexN1+k);
						System.out.println("Oh nein!");
					} while(randomNodeIndex == indexN1 && doesEdgeExist(e.getN1(),nodes.get(randomNodeIndex)));

					e.setN2(nodes.get(randomNodeIndex));
				} else {
					int randomNodeIndex = indexN2;
					do {
						randomNodeIndex = randInt(indexN2-k,indexN2+k);
						System.out.println("Oh nein!");
					} while(randomNodeIndex == indexN1 && doesEdgeExist(e.getN2(),nodes.get(randomNodeIndex)));

					e.setN1(nodes.get(randomNodeIndex));
				}
			}
		}
	}
	protected Edge getRegularEdge() {
		Edge regularEdge = null;

		ArrayList<Edge> regularEdges = new ArrayList<Edge>();
		for(Edge e : edges) {
			if(k >= Math.abs(getNodeIndex(e.getN1()) - getNodeIndex(e.getN2()))) {
				regularEdges.add(e);
			}
		}
		if(regularEdges.size() > 0) {
			int randomEdgeIndex = randInt(0,regularEdges.size()-1);
			regularEdge = regularEdges.get(randomEdgeIndex);
		}

		return regularEdge;
	}
	protected Edge getIrregularEdge() {
		Edge irregularEdge = null;
		ArrayList<Edge> irregularEdges = new ArrayList<Edge>();
		for(Edge e : edges) {
			if(k < Math.abs(getNodeIndex(e.getN1()) - getNodeIndex(e.getN2()))) {
				irregularEdges.add(e);
			}
		}
		if(irregularEdges.size() > 0) {
			int randomEdgeIndex = randInt(0,irregularEdges.size()-1);
			irregularEdge = irregularEdges.get(randomEdgeIndex);
		}

		return irregularEdge;
	}
	protected boolean doesEdgeExist(Node n1, Node n2) {
		for(Edge e : edges) {
			if((e.getN1() == n1 && e.getN2() == n2) || (e.getN1() == n2 && e.getN2() == n1)) return true;
		}
		return false;
	}
	public int getNumberNodes() { return nodes.size(); }

	public void makeRegular() {
		edges.clear();

		for(int i = 0; i < nodes.size(); i++) {
			for(int j = 1; j <= k; j++) {
				edges.add(new Edge(nodes.get(i), nodes.get((i+j) % nodes.size())));
			}
		}
	}

	public ArrayList<Node> getNeighbors(Node n) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		for(Edge e : edges) {
			if(e.getN1() == n) neighbors.add(e.getN2());
			if(e.getN2() == n) neighbors.add(e.getN1());
		}

		return neighbors;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}

package sw.graph;

import java.util.ArrayList;

public class Graph {
	protected ArrayList<Node> nodes;
	protected ArrayList<Edge> edges;
	public Graph(int numNodes) {
		nodes = new ArrayList<Node>(numNodes);
		for(int i = 0; i < numNodes; i++) {
			nodes.add(new Node());
		}
		edges = new ArrayList<Edge>(numNodes-1);

		makeRegular();
	}
	public void setNumberNodes(int newNo) {
		System.out.println(newNo + " " + getNumberNodes());
		for(int i = 0; i < Math.abs(newNo - getNumberNodes()); i++) {
			if(newNo > getNumberNodes()) {
				nodes.add(new Node());
			} else {
				nodes.remove(0);
			}
		}

		makeRegular();
	}
	public int getNumberNodes() { return nodes.size(); }

	public void makeRegular() {
		edges.clear();

		for(int i = 0; i < nodes.size() - 1; i++) {
			edges.add(new Edge(nodes.get(i), nodes.get(i+1)));
		}
		edges.add(new Edge(nodes.get(nodes.size()-1), nodes.get(0)));
	}
}

package sw.graph;

public class Edge {
	public Edge(Node n1, Node n2) {
		this.n1 = n1;
		this.n2 = n2;
	}
	public Node getN1() { return n1; }
	public Node getN2() { return n2; }
	public void setN1(Node n1) { this.n1 = n1; }
	public void setN2(Node n2) { this.n2 = n2; }

	Node n1;
	Node n2;
}

package sw.graph;

public class Node {
	protected InfectionState state;

	public Node() {
		state = InfectionState.SUSCEPTIBLE;
	}

	public InfectionState getInfectionState() { return state; }
	public void setInfectionState(InfectionState state) { this.state = state; }
}

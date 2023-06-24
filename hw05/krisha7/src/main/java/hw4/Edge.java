package hw4;

public class Edge<N extends Comparable<N>, L extends Comparable<L>> implements Comparable<Edge<N,L>> {
	
	/**
		Graph is a fixed object that holds a labeled directed edge connecting two nodes.
		
		Representation Invariant: The beginning node, ending node, and label cannot be null.
		
		Abstraction Function: There is a node called "ex" that has two nodes represented by "ex.start" and "ex.end," and a label for the edge represented by "ex.label.	
 	*/
	
	
	private final N parentNode;
	private final L label;
	private final N childNode;

	
	/**
		@param: node1 -> start-node of the new Edge
		@param: node2 -> end-node of the new Edge
		@param: lab -> length of the new Edge
		@effects: establishes a label lab for a newly created edge that connects node1 to node2
	 */
	public Edge(N node1, N node2, L lab) {
		label = lab;
		parentNode = node1;
		childNode = node2;
		checkRep();
	}
	
	
	/**
		@return: this Edge object's label
	*/
	public L getLabel() {
		return label;
	}
		
	/**
		@return: this Edge object's parent Node
	 */
	public N getParent() {
		return parentNode;
	}
	
	
	/**
		@return: this Edge object's child Node
	 */
	public N getChild() {
		return childNode;
	}
	
	

	/**
		@throw: if the object violates the representation invariant 
	 */
	private void checkRep() throws RuntimeException {
		
	}
	
	
	
	/** 
		@param: an Edge other_edge for this edge to be compared to
		@requires: other_edge is not null
		@return: if this = other_edge returns 0, if this > other_edge returns positive number, or if this < other_edge returns negative number.
	 */
	@Override
    public int compareTo(Edge<N,L> other_edge) {
        if (parentNode.equals(other_edge.getParent()) && childNode.equals(other_edge.getChild()) ) {
            return label.compareTo(other_edge.label);
        } else if (parentNode.equals(other_edge.getParent()) ) {
            return childNode.compareTo(other_edge.getChild());
        } else {
            return parentNode.compareTo(other_edge.getParent());
        }
    }
	
}
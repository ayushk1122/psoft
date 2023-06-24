package hw4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Graph<N extends Comparable<N>, L extends Comparable<L>> {
	
	/**
	Graph is a modifiable structure that consists of Nodes (which are String objects) and Edges (which are implemented as a distinct class).
	
	Representation Invariant: Nodes within the graph must be distinct, and Edges should only connect Nodes that exist within the graph.
	
	Abstraction Function: Graph can be seen as a container of Nodes and Edges. Specifically, the Graph is represented by the collection of 
	Nodes denoted by "ex.nodes," and the collection of Edges denoted by "ex.edges."

		
 	*/
	
	
	private HashMap<N, HashSet<Edge<N,L>>> graph;
	
	
	
	/**
		@effects: Constructs new empty Graph
	 */
	public Graph(){
		graph = new HashMap<N, HashSet<Edge<N,L>>>();
		checkRep();
	}
	
	/**
		@param: new edge new_edge is added
		@modifies: edges
		@effects: new_edge added to edges
		@throws: If the graph does not contain either of the two nodes stored in the new_edge.	    
	*/
	public void addEdge(Edge<N,L> new_edge) {
		if ((graph.keySet().contains(new_edge.getParent())) == false) {
			throw new RuntimeException("addEdge1");
		}
		if ((graph.keySet().contains(new_edge.getChild())) == false) {
			throw new RuntimeException("addEdge2");
		}
		
		HashSet<Edge<N,L>> temp = graph.get(new_edge.getParent());
		temp.add(new_edge);
		checkRep();
	}
	
	
	/**
		@param: adds new node new_node 
		@modifies: nodes
		@effects: if new_node is not already present, adds new_node to nodes 
		@throws: if new_node is already present in the graph or is null

	 */
	public void addNode(N new_node) {
		if (graph.keySet().contains(new_node)) {
			throw new RuntimeException("addNode");
		}
		
		graph.put(new_node, new HashSet<Edge<N,L>>());
		checkRep();
	}
	
	/**
		@return: this Graph object's Edges
	 */
	public HashSet<Edge<N,L>> getEdges() {
		HashSet<Edge<N,L>> temp = new HashSet<Edge<N,L>>();
		
		for (N key : graph.keySet()) {
			temp.addAll(graph.get(key));
		}
		
		return temp;
	}

	
	/**
		@return: this Graph object's Nodes
	 */
	public Set<N> getNodes() {
		return graph.keySet();
	}
	
	
	/**
		@return: this Graph object's number of Edges
	 */
	public int numEdges() {
		int temp = 0;
		
		for (N key : graph.keySet()) {
			temp += graph.get(key).size();
		}
		
		return temp;
	}
	
	/**
		@return: this Graph object's number of Nodes
	 */
	public int numNodes() {
		return graph.size();
	}
	
	
	/**
		@modifies: nodes and edges
		@effects: clears nodes and edges
	 */
	public void clear() {
		graph.clear();
		checkRep();
	}
	
	
	/**
		@param: String node that represents a parent node for the edges
		@returns: sorts edges that start from the node and are sorted using a TreeSet
	 */
	public TreeSet<Edge<N,L>> listChildrenSorted(N node) {
		return new TreeSet<Edge<N,L>>(graph.get(node));
	}
	
	
	/**
		@param: node n to find in nodes
		@return: if node n in nodes return true, return false otherwise
	 */
	public boolean contains(N n){
		return graph.keySet().contains(n);
	}
	
	
	/**
		@throw: if the object violates the representation invariant 
	 */
	private void checkRep() throws RuntimeException {
		
	}
	
	
}

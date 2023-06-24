package hw7;

import java.util.LinkedList;

import hw4.Edge;


public class Path implements Comparable<Path>{
	private Double Cost;
	private LinkedList<Edge<Building,Double>> edges;
	private Building Start;
	private Building End;

	/**
		@param: Building start designating first point in Path
		@param: Building end designating last point in Path
		@effects: Constructs new Path following specifications
	 */
	public Path(Building start, Building end){
		Cost = 0.0;
		edges = new LinkedList<Edge<Building,Double>>();
		Start = start;
		End = end; 
	}
	
	/**
		@param: Path other to be copied
		@effects: Copies existing Path
	 */
	public Path(Path other){
		Cost = other.Cost; 
		edges = new LinkedList<Edge<Building,Double>>(other.edges);
		Start = other.Start;
		End = other.End;
	}
	
	/**
		@param: Edge edge to be added to path
		@modifies: edges, End and Cost
		@effects: adds edge to list of edges, sets child of edge as last point in Path and adds cost of
					edge to total
	 */
	public void addEdge(Edge<Building, Double> edge) {
		Cost += edge.getLabel();
		edges.add(edge);
		End = edge.getChild();
	}
	
	/**
		@return: this Path's total cost
	 */
	public Double cost() {
		return Cost;
	}
	
	/**
		@return: this Path's starting position
	 */
	public Building start() {
		return Start;
	}
	
	/**
		@return: this Path's ending position
	 */
	public Building end() {
		return End;
	}
	
	/** 
		@param: Path other to be compared
		@requires: other is non empty
		@return: 0 if this == other, a positive number if this > other or a negative number if this < other.
	 */
	@Override
	public int compareTo(Path other) { 
		return Cost.compareTo(other.Cost);
	}
	
	/**
		@return: this Path's list of Edges
	 */
	public LinkedList<Edge<Building,Double>> returnEdges() {
		return new LinkedList<Edge<Building,Double>>(edges);
	}
}
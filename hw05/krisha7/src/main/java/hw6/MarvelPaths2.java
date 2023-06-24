package hw6;

import hw4.Graph;
import hw5.MarvelParser;

import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.LinkedList;
import java.util.Map;
import java.io.IOException;

import hw4.Edge;

public class MarvelPaths2 {
	
	/**
	MarvelPaths is an object that can be changed, and it comprises of two objects: a Graph object and a Map object. 
	The Map object stores information about books and characters. The Graph object represents characters and their relationships, 
	with nodes representing characters and edges representing their presence in the same book.
	
	Representation Invariant: The keys of books contain all labels of edges.
	
	Abstraction Function: The graph consists of a collection of nodes that represent characters and a collection of edges 
	that represent when characters appear in the same book.
	
	*/
	
	private HashMap<String, Set<String>> books;
	private Graph<String, String> graph;
	private Graph<String, Double> weight_graph;
	
	
	/**
		@effects: Constructs new empty MarvelPaths
	 */
	public MarvelPaths2() {
		books = new HashMap<String, Set<String>>();
		graph = new Graph<String, String>();
		weight_graph = new Graph<String, Double>();
	}
	
	/**
		@param: a string char to be added
		@param: the string book the character was in
		@modifies: graph and books
		@effects: The method updates the graph by adding the new character to it, and also updates the books data 
		structure by adding the book to it if it is not already present. Additionally, it creates edges between 
		the new character and all other characters in the same book, so that they are connected in the graph.
	 */
	public void addChar(String character, String book){
		if (!graph.contains(character)) {
			graph.addNode(character);
		}	
		
		if (books.containsKey(book)) {
			
			for (String otherChar : books.get(book)) {
				if (!character.equals(otherChar)) {
					graph.addEdge(new Edge<>(character, otherChar, book));
					graph.addEdge(new Edge<>(otherChar, character, book));
				}
			}
			
		} else {
			books.put(book, new HashSet<>());
		}
		
		books.get(book).add(character);
	}
	
	/** 
	@param: a string file that contains the name of a file stored in the data,
	@effects: this method creates a new graph by parsing the data in the file
	@throws: The method may throw an IOException if there are errors during the parsing process, 
	as implemented in the MarvelParser class.
	 */
	public void createNewGraph(String filename) {
	    clear();
	    
	    try {
	        Set<String> characters = new HashSet<>();
	        Map<String, Map<String, Double>> characterConnections = new HashMap<>();
	        MarvelParser.readData(filename, books, characters);
	        
	        for (String character : characters) {
	            graph.addNode(character);
	            weight_graph.addNode(character);
	            characterConnections.put(character, new HashMap<String, Double>());
	        }
	        
	        for (String book : books.keySet()) {
	            Set<String> charactersInBook = books.get(book);
	            
	            for (String character1 : charactersInBook) {
	                for (String character2 : charactersInBook) {
	                    if (!character1.equals(character2)) {
	                        graph.addEdge(new Edge<String, String>(character1, character2, book));
	                        
	                        Map<String, Double> connections = characterConnections.get(character1);
	                        Double weight = connections.get(character2);
	                        
	                        if (weight == null) {
	                            weight = 0.000;
	                        }
	                        
	                        connections.put(character2, weight + 1);
	                    }
	                }
	            }
	        }
	        
	        for (String character1 : characterConnections.keySet()) {
	            Map<String, Double> connections = characterConnections.get(character1);
	            
	            for (String character2 : connections.keySet()) {
	                weight_graph.addEdge(new Edge<String, Double>(character1, character2, 1 / connections.get(character2)));
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
		@return: number of nodes in graph
	 */
	public int numNodes(){
		return graph.numNodes();
	}
	
	
	/**
		@return: number of edges in graph
	 */
	public int numEdges(){
		return graph.numEdges();
	}
	
	
	/**
		@return: number of books in the MarvelPaths object
	 */
	public int numBooks(){
		return books.size();
	}
	
	/**
		@param: An initial node, denoted as node1, from which the path will commence.
		@param: A terminal node, denoted as node2, at which the path will conclude.
		@return: a string enumerating all of the nodes/edges visited along shortest path and total weight of path
	 */
	public String findPath(String startNode, String endNode){
		if (!graph.contains(startNode) && !startNode.equals(endNode) && !graph.contains(endNode)) {
			return "unknown character " + startNode + "\nunknown character " + endNode + "\n";
		} else if (!graph.contains(startNode)) {
			return "unknown character " + startNode + "\n";
		} else if (!graph.contains(endNode)) {
			return "unknown character " + endNode + "\n";
		}
	    
	    String result = "path from " + startNode + " to " + endNode + ":\n";
	    PriorityQueue<Path> pq = new PriorityQueue<Path>();
	    Map<String, Path> visited = new HashMap<String, Path>();
	    
	    Path minPath = new Path(startNode, startNode);
	    pq.add(minPath);
	    
	    while (pq.size() != 0) {
	        minPath = pq.poll();
	        String minDest = minPath.endNode;
	        
	        if (minDest.equals(endNode)) {
	            for (Edge<String, Double> edge : minPath.edgesList) {
	            	result += edge.getParent() + " to " + edge.getChild() + String.format(" with weight %.3f", edge.getLabel()) + "\n";
	            }
	            
	            return result + String.format("total cost: %.3f\n", minPath.totalWeight);
	            
	        } else if (visited.containsKey(minDest)) {
	            continue;
	        }
	        
	        for (Edge<String, Double> edge : weight_graph.listChildrenSorted(minDest)) {
	            Path nextPath = new Path(minPath);
	            nextPath.edgesList.add(edge);
	            nextPath.endNode = edge.getChild();
	            nextPath.totalWeight += edge.getLabel();
	            pq.add(nextPath);
	        }
	        
	        visited.put(minDest, minPath);
	    }
	    
	    return result + "no path found\n";
	}
	

	

	public class Path implements Comparable<Path>{
	    public String startNode;
	    public String endNode;
	    public Double totalWeight;
	    public LinkedList<Edge<String,Double>> edgesList;
	    
		public Path(Path otherPath){
	        totalWeight = otherPath.totalWeight;
	        edgesList = new LinkedList<Edge<String,Double>>(otherPath.edgesList);
	        startNode = new String(otherPath.startNode);
	        endNode = new String(otherPath.endNode);
		}
		public Path(String start, String end){
		    totalWeight = 0.00;
	        edgesList = new LinkedList<Edge<String,Double>>();
	        startNode = new String(start);
	        endNode = new String(end);
		}
		

	    @Override
	    public int compareTo(Path otherPath) {
	        return totalWeight.compareTo(otherPath.totalWeight);
	    }
	    
	}

	
	/**
		@modifies: graph and books
		@effects: clears graph and books
    */
	public void clear() {
		books.clear();
		graph.clear();
		weight_graph.clear();
	}
}
package hw5;

import hw4.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;

import hw4.Edge;

public class MarvelPaths {
	
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
	
	
	/**
		@effects: Constructs a new empty MarvelPaths
	 */
	public MarvelPaths() {
		books = new HashMap<String, Set<String>>();
		graph = new Graph<String, String>();
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
		   HashSet<String> characters = new HashSet<String>();
		   MarvelParser.readData(filename, books, characters);
		    
		   for (String character : characters) {
		     graph.addNode(character);
		   }
		    
		   for (String book : books.keySet()) {
		     Set<String> charactersInBook = books.get(book);
		      
		     for (String character1 : charactersInBook) {
		       for (String character2 : charactersInBook) {
		         if (!character1.equals(character2)) {
		           graph.addEdge(new Edge<String, String>(character1, character2, book));
		         }
		       }
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
	@return: number of books in the MarvelPaths object
	 */
	public int numBooks(){
		return books.size();
	}
	
	/**
		@return: number of edges in graph
	 */
	public int numEdges(){
		return graph.numEdges();
	}
	
	
	public static void MemUse() {
	     Runtime runtime = Runtime.getRuntime();
	     runtime.gc(); // Run the garbage collector

	     long memory = runtime.totalMemory() - runtime.freeMemory();
	     System.out.println("Used memory is bytes: " + memory);
	}

	/**
	@param: An initial node, denoted as node1, from which the path will commence.
	@param: A terminal node, denoted as node2, at which the path will conclude.
	@return: A string that enumerates all the nodes and edges traversed on the shortest path between node1 and node2.
	 */
	public String findPath(String startNode, String endNode){
	    if (!graph.contains(startNode) && !startNode.equals(endNode) && !graph.contains(endNode)) {
	    	return "unknown character " + startNode + "\nunknown character " + endNode + "\n";
	    } else if (!graph.contains(startNode)) {
	    	return "unknown character " + startNode + "\n";
	    } else if (!graph.contains(endNode)) {
	    	return "unknown character " + endNode + "\n";
	    }

	    String shortestPath = "path from " + startNode + " to " + endNode + ":\n";
	    Map<String, LinkedList<Edge<String, String>>> shortestPaths = new HashMap<String, LinkedList<Edge<String, String>>>();
	    LinkedList<String> queue = new LinkedList<String>();

	    shortestPaths.put(startNode, new LinkedList<Edge<String, String>>());
	    queue.add(startNode);

	    while (queue.size() > 0) {
	        String currentNode = queue.pop();

	        if (currentNode.equals(endNode)) {
	            for (Edge<String, String> edge : shortestPaths.get(currentNode)) {
	                shortestPath += edge.getParent() + " to " + edge.getChild() + " via " + edge.getLabel() + "\n";
	            }
	            return shortestPath;
	        }

	        boolean breakNext = false;

	        Iterator<Edge<String, String>> iterator = graph.listChildrenSorted(currentNode).iterator();
	        while (iterator.hasNext()) {
	            Edge<String, String> edge = iterator.next();

	            if (currentNode.equals(edge.getParent())) {
	                breakNext = true;

	                LinkedList<Edge<String, String>> nextPath = new LinkedList<Edge<String, String>>(shortestPaths.get(currentNode));
	                nextPath.addLast(edge);

	                if (!shortestPaths.containsKey(edge.getChild())) {
	                    shortestPaths.put(edge.getChild(), nextPath);
	                    queue.add(edge.getChild());
	                } else if (shortestPaths.get(edge.getChild()).size() > nextPath.size()) {
	                    shortestPaths.put(edge.getChild(), nextPath);
	                }

	            } else if (breakNext) {
	                breakNext = false;
	                break;
	            }
	        }
	    }

	    return shortestPath + "no path found\n";
	}
	
	/**
	@modifies: graph and books
	@effects: clears graph and books
    */
	public void clear() {
		books.clear();
		graph.clear();
	}
	
	public static void main(String[] arg) {
		// starting time 
        long start = System.currentTimeMillis();
        
		MarvelPaths test = new MarvelPaths();
		test.createNewGraph("data/two_connected_nodes.csv");
		System.out.println(test.numEdges());
		System.out.println(test.numNodes());
		System.out.println(test.numBooks());
		
		// ending time 
        long end = System.currentTimeMillis(); 
        System.out.println("takes " + (end - start) + "ms"); 
    	MemUse();
    	
    	
		// starting time 
        start = System.currentTimeMillis();
        

		test.createNewGraph("data/marvel.csv");
    	System.out.println(test.findPath("WALRUSS", "OSBORN, LIZ ALLAN"));
		
		// ending time 
        System.currentTimeMillis(); 
        System.out.println("takes " + (end - start) + "ms"); 
    	MemUse();
    }
}
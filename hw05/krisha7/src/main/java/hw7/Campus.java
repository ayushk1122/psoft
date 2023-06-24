package hw7;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.io.IOException;
import java.util.PriorityQueue;


import hw4.Edge;
import hw4.Graph;

public class Campus {
	private Map<String, String> Name_ID;
	private Map<String, Building> ID_Building;
	private Graph<Building, Double> graph;
	
	/**
		@effects: Constructs new empty Campus
	 */
	public Campus(){
		Name_ID = new HashMap<String, String>();
		ID_Building = new HashMap<String, Building>();
		graph = new Graph<Building, Double>();
	}
	
	
	/**
		@return: String listing all buildings in Campus in order
	 */
	public String listBuildings() {
		String buildingsList = "";
		
		TreeSet<Building> buildingsSet = new TreeSet<Building>(graph.getNodes());
		
		for (Building building : buildingsSet) {
			if (!building.name().equals("")) {
				buildingsList += building.name() + "," + building.id() + "\n";
			}
		}
		
		return buildingsList;
	}
	
	
	/**
		@param: directed Edge edge
		@return: String stating direction moved by edge
	 */
	public String getDirection(Edge<Building, Double> edge) {
	    Building bldg1 = edge.getParent();
	    Building bldg2 = edge.getChild();
	        
	    double x1 = bldg1.x();
	    double x2 = bldg2.x();
	    double y1 = bldg1.y();
	    double y2 = bldg2.y();
	    double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
	        
	    if (angle < -157.5 || angle >= 157.5) {
	        return "North";
	    } else if (angle < -112.5) {
	        return "NorthWest";
	    } else if (angle < -67.5) {
	        return "West";
	    } else if (angle < -22.5) {
	        return "SouthWest";
	    } else if (angle < 22.5) {
	        return "South";
	    } else if (angle < 67.5) {
	        return "SouthEast"; 
	    } else if (angle < 112.5) {
	        return "East";
	    } else {
	        return "NorthEast";
	    }
		
	}
	
	/** 
		@param: String node_file containing name of file storing node data
		@param: String edge_file containing name of file storing edge data
		modifies: graph, ID_Building and Name_ID
		@effects: creates new Campus from data in the files
		@throws: IOException through CampusParser
	 */
	public void createNewCampus(String nodeFile, String edgeFile) {
	    clear();
	    
	    try {
	        Set<Building> buildings = new TreeSet<>();
	        CampusParser.readNodeData(nodeFile, buildings);
	        
	        for (Building b : buildings) {
	        	Name_ID.put(b.name(), b.id());
	        	ID_Building.put(b.id(), b);
	            graph.addNode(b);
	        }
	        
	        List<List<String>> buildingEdges = new LinkedList<>();
	        CampusParser.readEdgeData(edgeFile, buildingEdges);
	        
	        for (List<String> edge : buildingEdges) {
	            Building node1 = ID_Building.get(edge.get(0));
	            Building node2 = ID_Building.get(edge.get(1));
	            
	            Double x1 = node1.x();
	            Double x2 = node2.x();
	            Double y1 = node1.y();
	            Double y2 = node2.y();
	            Double label = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	            graph.addEdge(new Edge<Building, Double>(node1, node2, label));
	            graph.addEdge(new Edge<Building, Double>(node2, node1, label));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
		@param: starting node node1 for the path
		@param: destination node node2 for the path
		@return: String listing all nodes and directions traveled and visited along the shortest path
	 */
	public String findPath(String node1, String node2) {
	    Building start;
	    try {
	        Integer.parseInt(node1);
	        start = ID_Building.get(node1);
	
	    } catch (Exception e) {
	        start = ID_Building.get(Name_ID.get(node1));
	    }
	
	    Building end;
	    try {
	        Integer.parseInt(node2);
	        end = ID_Building.get(node2);
	
	    } catch (Exception e) {
	        end = ID_Building.get(Name_ID.get(node2));
	    }
	
	    String output = "";
	
	    if (start == null && end == null && node1.equals(node2)) {
	        return output + "Unknown building: [" + node1 + "]\n";
	
	    } else if (start == null && end == null) {
	        output += "Unknown building: [" + node1 + "]\n";
	        return output + "Unknown building: [" + node2 + "]\n";
	
	    } else if (start == null && end != null) {
	        return output + "Unknown building: [" + node1 + "]\n";
	
	    } else if (start != null && end == null) {
	        return output + "Unknown building: [" + node2 + "]\n";
	    }
	
	    if (start.name().equals("") && end.name().equals("") && node1.equals(node2)) {
	        return output + "Unknown building: [" + node1 + "]\n";
	
	    } else if (start.name().equals("") && end.name().equals("")) {
	        output += "Unknown building: [" + node1 + "]\n";
	        return output + "Unknown building: [" + node2 + "]\n";
	
	    } else if (start.name().equals("") && !end.name().equals("")) {
	        return output + "Unknown building: [" + node1 + "]\n";
	
	    } else if (!start.name().equals("") && end.name().equals("")) {
	        return output + "Unknown building: [" + node2 + "]\n";
	    }
	
	    PriorityQueue<Path> active = new PriorityQueue<>();
	    Map<Building, Path> finished = new HashMap<>();
	
	    Path minPath = new Path(start, start);
	    active.add(minPath);
	
	    while (!active.isEmpty()) {
	        minPath = active.poll();
	        Building minDest = minPath.end();
	
	        if (minDest.compareTo(end) == 0) {
	            output += "Path from " + start.name() + " to " + end.name() + ":\n";
	
	            for (Edge<Building, Double> edge : minPath.returnEdges()) {
	                String direction = getDirection(edge);
	                String buildingName = edge.getChild().name();
	
	                if (buildingName.equals("")) {
	                    output += "\tWalk " + direction + " to (Intersection " + edge.getChild().id() + ")\n";
	                } else {
	                    output += "\tWalk " + direction + " to (" + buildingName + ")\n";
	                }
	            }
	
	            return output + String.format("Total distance: %.3f pixel units.\n", minPath.cost());
	
	        } else if (finished.containsKey(minDest)) {
	            continue;
	        }
	
	        for (Edge<Building, Double> edge : graph.listChildrenSorted(minDest)) {
	
	            Building parent = edge.getParent();
	            Building child = edge.getChild();
	
	            if (finished.containsKey(parent) || finished.containsKey(child)) {
	                continue;
	            }
	
	            Path next_path = new Path(minPath);
	            next_path.addEdge(edge);
	            active.add(next_path);
	        }
	
	        finished.put(minDest, minPath);
	    }
		return output + "There is no path from " + start.name() + " to " + end.name() + ".\n";
	}
	
	/**
		@modifies: graph, ID_Building and Name_ID
		@effects: clears graph, ID_Building and Name_ID
	 */
	public void clear(){
		Name_ID.clear();
		ID_Building.clear();
		graph.clear();
	}
}
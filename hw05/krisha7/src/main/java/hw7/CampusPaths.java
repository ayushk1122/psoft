package hw7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CampusPaths {
	
	
	public static void main(String[] argv) {	
	    // Create a new instance of the Campus class
	    Campus myCampus = new Campus();

	    // Load campus data from CSV files
	    myCampus.createNewCampus("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");

	    // Create a buffered reader to read user input
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    String input = "";

	    // Continue reading user input until "q" is entered
	    while (!input.equals("q")) {
	        try {
	            // Read the user's input
	            input = reader.readLine();
	        } catch (IOException e) {
	            break;
	        }

	        // Process the user's input
	        if (input.equals("q")) {
	            // Quit the program if "q" is entered
	            break;
	        } else if(input.equals("b")) {
	            // List all buildings in lexicographic order
	            System.out.print(myCampus.listBuildings());
	        } else if(input.equals("m")) {
	            // Display a menu of available commands
	            System.out.println("Menu:");
	            System.out.println("b lists all buildings (only buildings) in the form name,id in lexicographic (alphabetical) order of name.");
	            System.out.println("r prompts the user for the ids or names of two buildings and prints directions for the shortest route between them.");
	            System.out.println("q quits the program.");
	            System.out.println("m prints a menu of all commands.");
	        } else if(input.equals("r")) {
	            // Find the shortest path between two buildings
	            System.out.print("First building id/name, followed by Enter: ");
	            String start;
	            try {
	                start = reader.readLine();
	            } catch (IOException e) {
	                break;
	            }

	            System.out.print("Second building id/name, followed by Enter: ");
	            String end;
	            try {
	                end = reader.readLine();
	            } catch (IOException e) {
	                break;
	            }

	            System.out.print(myCampus.findPath(start, end));
	        } else {
	            // Display an error message for unknown commands
	            System.out.println("Unknown option");
	        }
	    }
		
	}
}
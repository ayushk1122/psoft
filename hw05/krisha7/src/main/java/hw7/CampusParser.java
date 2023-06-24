package hw7;

import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Set;
import java.io.IOException;
import java.io.FileReader;

public class CampusParser {
	
	/** @param: "filename" represents the file path to the "CSV" file that includes pairs of <hero, book>.                                                                                              
		@param: "building_edges" refers to the List of Lists used to store the destination vertices for the edges.
		@effects: operation will append the IDs of the edge endpoints to be constructed
		@throws: IOException will be thrown if the specified file cannot be read or if the file is not properly formatted.                                                                                  
	*/
	public static void readEdgeData(String filename,  List<List<String>> building_edges) 
			throws IOException {
		
	    // Open the file for reading
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

	    // Read each line in the file
	    String line = null;
	    while ((line = bufferedReader.readLine()) != null) {
	        // Split each line into fields
	        String[] fields = line.split(",");

	        // Create a list to store the fields as strings
	        List<String> edge = new LinkedList<String>();

	        // Add the two fields to the list
	        edge.add(fields[0]);
	        edge.add(fields[1]);

	        // Add the list to the building_edges list
	        building_edges.add(edge);
	    }

	    // Close the file
	    bufferedReader.close();
	}

	/** @param: a string representing the file path to a CSV file containing <hero, book> pairs                                                                                               
    	@param: Set object called "buildings" that will store all the buildings found in the file
    	@effects:  adding Building objects to the Set "buildings"
    	@throws: if the file cannot be read or is not properly formatted, the method will throw an IOException.                                                                                 
	 */
	public static void readNodeData(String filename, Set<Building> buildings) 
			throws IOException {

		// Open the file for reading
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		// Read each line of the file
		String line = null;
		while ((line = br.readLine()) != null) {
			// Parse the fields
			String[] parts = line.split(",");

			// Extract the data
			Double x = Double.parseDouble(parts[2]);
			Double y = Double.parseDouble(parts[3]);
			
			// Create a new building object and add it to the set
			Building b = new Building(parts[0], parts[1], x, y);
			buildings.add(b);
		}
		
		// Close the file
		br.close();
	}
}
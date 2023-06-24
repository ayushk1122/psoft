package hw7;

import java.io.*;
import static org.junit.Assert.*;
import org.junit.Test;

import hw4.Edge;
import hw4.GraphWrapper;

public class CampusPathsTest { // Rename to the name of your "main" class

	/**
	 * @param file1 
	 * @param file2
	 * @return true if file1 and file2 have the same content, false otherwise
	 * @throws IOException
	 */	
	/* compares two text files, line by line */
	private static boolean compare(String file1, String file2) throws IOException {
		BufferedReader is1 = new BufferedReader(new FileReader(file1)); // Decorator design pattern!
		BufferedReader is2 = new BufferedReader(new FileReader(file2));
		String line1, line2;
		boolean result = true;
		while ((line1=is1.readLine()) != null) {
			// System.out.println(line1);
			line2 = is2.readLine();
			if (line2 == null) {
				System.out.println(file1+" longer than "+file2);
				result = false;
				break;
			}
			if (!line1.equals(line2)) {
				System.out.println("Lines: "+line1+" and "+line2+" differ.");
				result = false;
				break;
			}
		}
		if (result && is2.readLine() != null) {
			System.out.println(file1+" shorter than "+file2);
			result = false;
		}
		is1.close();
		is2.close();
		return result;
	}
	
	private void runTest(String filename) throws IOException {
		InputStream in = System.in;
		PrintStream out = System.out;
		String inFilename = "data/"+filename+".test"; // Input filename: [filename].test here  
		String expectedFilename = "data/"+filename+".expected"; // Expected result filename: [filename].expected
		String outFilename = "data/"+filename+".out"; // Output filename: [filename].out
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(inFilename));
		System.setIn(is); // redirects standard input to a file, [filename].test 
		PrintStream os = new PrintStream(new FileOutputStream(outFilename));
		System.setOut(os); // redirects standard output to a file, [filename].out 
		CampusPaths.main(null); // Call to YOUR main. May have to rename.
		System.setIn(in); // restores standard input
		System.setOut(out); // restores standard output
		assertTrue(compare(expectedFilename, outFilename)); 
		// TODO: You can implement more informative file comparison, if you would like.
		
	}
	
	@Test
	public void testListBuildings() throws IOException {
		runTest("test1");
	}
	
	@Test
	public void testPath() throws IOException{
		Building a = new Building("a", "b", 1.0, 2.0);
		Building b = new Building("c", "d", 3.0, 4.0);
		Path c = new Path(a, b);
		Path d = new Path(c);
		assert (c.cost() == 0.0);
		assert(c.start() == a);
		assert(c.end() == b);
		assert(c.compareTo(d) == 0);
		c.returnEdges();
		Edge<Building, Double> e = new Edge<Building, Double>(a, a, 2.0);
		c.addEdge(e);
	}
	
	@Test
	public void campusTest() throws IOException{
		Building a = new Building("a", "b", 1.0, 2.0);
		Building b = new Building("c", "d", 3.0, 4.0);
		Campus c = new Campus();
		c.listBuildings();
		c.findPath("a", "b");
		c.findPath("", "");
		c.findPath("1", "2");
		c.findPath("100000", "q");
		c.findPath("-100000", "-200000");
		c.findPath("67", "76");
		Edge<Building, Double> e = new Edge<Building, Double>(a, a, 2.0);
		c.getDirection(e);
		Edge<Building, Double> f = new Edge<Building, Double>(b, b, 10.0);
		c.getDirection(f);
		CampusPaths p = new CampusPaths();
	}
	
}

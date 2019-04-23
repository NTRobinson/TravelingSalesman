package salesman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.FileReader;
import util.Graph;
import util.Vertex;

public class Driver 
{
	public static void main (String[] args) throws IOException
	{
		/*
		 * The "traveling salesman" must start in a specified city,
		 * travel to each of several other cities exactly once, 
		 * and return to the starting city following the path of least cost 
		 * (in this case, the shortest route). This is an "intractable" 
		 * problem in that the optimal solution is only guaranteed by 
		 * enumerating all of the possible paths (i.e. branch and bound). 
		 * However, there are many techniques that can find good solutions quickly.
		 */
		
		FileReader fr = new FileReader();
		
		ArrayList<String> verts_read = fr.readFile(args[0]); // verts, cities
		ArrayList<String> edges_read = fr.readFile(args[1]); // edges, mileages
		
		String start_key = verts_read.get(0); // starting (and ending) city
		
		// num genes per chromosome is implied by vertex info
		int num_pop = Integer.parseInt(args[2]); // population size
		int num_gen = Integer.parseInt(args[3]); // number of generations
		
		System.out.println("Population Size: " + num_pop);
		System.out.println("Number of Generations: " + num_gen);
		
		// optimal solution -- 5, 4, 9, 8, 7, 3, 11, 13, 10, 14, 1, 2, 6, 12 for 7810
		
		Graph<String> graph = new Graph<String>(verts_read.size() + 1); // constructs our graph given the number of vertices
		
		Iterator<String> itr = verts_read.iterator();
		while (itr.hasNext())
		{ // store our verts
			graph.addVertex((String )itr.next());
		}
		for (String string : edges_read)
		{ // adds in all of our edge values
			String[] edge_data = string.split(" "); // grabs the three stings and plops into array
			graph.addEdge(edge_data[0], edge_data[1], Double.parseDouble(edge_data[2])); // since we have edge info we can now add an edge
		}
		// vertices and edges are populated, graph is fully connected
		
		System.out.println("Number of Vertices: " + graph.size() + "\n");
		
		GeneticGraph genetic_graph = new GeneticGraph(graph, start_key, num_pop, num_gen);
		
		System.out.println();
		genetic_graph.printBest();
	}
}

package salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import util.Edge;
import util.Graph;
import util.Vertex;

public class Chromosome
{
	/* 
	 * Basically a "state", or order of cities.
	 * 
	 * A "chromosome" is a collection of genes. For the traveling salesman problem, 
	 * a chromosome can be initialized by obtaining a random permutation of the city indices, 
	 * omitting the starting city. 
	 * 
	 * The size of the chromosome depends on the number of cities in the city list.
	*/
	
	private int population_number;
	private int num_genes;
	private int fitness;
	
	String start_city;
	private List<Integer> genes;
	
	public Chromosome(List<Integer> gene_sequence, Graph<String> g, String start_city, int pop_num)
	{
		num_genes = gene_sequence.size();
		this.start_city = start_city;
		population_number = pop_num;
		fitness = 0;
		
		genes = new ArrayList<Integer>(gene_sequence);
		calcFitness(g);
	}
	
	private void calcFitness(Graph<String> graph)
	{ // will find the fitness
		ArrayList<Vertex<String>> verts = graph.getVertices();
		
		Vertex<String> start = null;
		Vertex<String> current = null;
		fitness = 0;
		
		for (Vertex<String> vert : verts)
		{ // get vertex to start calculating from
			if (vert.getKey().equals(start_city))
			{
				start = vert;
				current = vert;
			}
		}
		
		for (int vertex_index : genes)
		{
			// get edges of the current key
			LinkedList<Edge<String>> current_edges = current.getEdges();
			for(Edge<String> edge: current_edges)
			{ // find the next city
				if (edge.getEnd().getNumber() == vertex_index)
				{
					//System.out.println(edge.toString());
					fitness = (int) (fitness + edge.getWeight());
					current = edge.getEnd();
				}
			}
		}
		
		LinkedList<Edge<String>> current_edges = current.getEdges();
		for(Edge<String> edge: current_edges)
		{
			if (edge.getEnd().getNumber() == start.getNumber())
			{
				//System.out.println(edge.toString());
				fitness = (int) (fitness + edge.getWeight());
				current = edge.getEnd();
			}
		}
	}
	
	public boolean equal(Chromosome other)
	{
		if (other.getFitness() == this.fitness)
		{
			return true;
		}
		
		return false;
	}
	
	public List<Integer> getGenes()
	{
		return genes;
	}
	
	public void setGenes(List<Integer> new_genes)
	{
		genes = new ArrayList<Integer>(new_genes);
	}
	
	public void listGenes()
	{
		ArrayList<Integer> display_list = new ArrayList<Integer>();
		
		for (int i = genes.size() - 1; i >= 0; i--)
		{
			display_list.add(genes.get(i));
		}
		
		for (int out : display_list)
		{
			System.out.print(out + " ");
		}
		
		System.out.println();
	}
	
	public int getFitness()
	{ 	
		return fitness;
	}
	
	public int getNumGenes()
	{
		return num_genes;
	}
	
	public int getPopNumber()
	{
		return population_number;
	}
	
	public void setPopNumber(int pop_num)
	{
		population_number = pop_num;
	}
}

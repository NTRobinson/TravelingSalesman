package salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import util.Graph;
import util.Vertex;

public class GeneticGraph 
{
	private Graph<String> graph;
	private String start_key;
	private int num_pop, num_gen, gene_length;
	
	private ArrayList<Chromosome> population;
	private ArrayList<Chromosome> next_population;
	private Chromosome original_chromo;
	
	public GeneticGraph(Graph<String> g, String start_vertex_key, int num_population, int num_generations)
	{
		graph = g;
		start_key = start_vertex_key;
		
		num_pop = num_population;
		num_gen = num_generations;
		
		population = new ArrayList<Chromosome>();
		next_population = new ArrayList<Chromosome>();
		this.generatePopulation();
		
		List<Integer> gene_sequence = new ArrayList<Integer>();
		for (Vertex<String> vertex : graph.getVertices())
		{
			if (!vertex.getKey().equals(start_key))
			{ 
				gene_sequence.add(vertex.getNumber());
			}
		}
		original_chromo = new Chromosome(gene_sequence, graph, start_key, -1);
		gene_length = gene_sequence.size();
		
		
		for(int i = 0; i < num_gen; i++)
		{
			if(i%50 == 0)
			{
				System.out.println("generation - " + i);
			}
			
			this.nextPopulation();
		}
	}
	
	private void generatePopulation()
	{		
		for (int i = 0; i < num_pop; i++)
		{ // generate a population of chromosomes equal to num_pop
			List<Integer> gene_sequence = new ArrayList<Integer>();
			for (Vertex<String> vertex : graph.getVertices())
			{
				if (!vertex.getKey().equals(start_key))
				{ 
					gene_sequence.add(vertex.getNumber());
				}
			}
			Collections.shuffle(gene_sequence);
			Chromosome chromo = new Chromosome(gene_sequence, graph, start_key, i);
			
			population.add(chromo);
		}
	}
	
	public void nextPopulation()
	{ // creates the next generation of chromosomes
		population = sortPopulation(population);
		next_population = new ArrayList<Chromosome>();
		
		for(int i = 0; i < 10; i++)
		{ // get the first ten, unaltered, in the next population
			next_population.add(population.get(i));
		}
		
		for(int i = 0; i < (num_pop - 10); i++)
		{ // for the remaining population slots (num_pop - 10)
			boolean unique = false;
			while(unique == false)
			{
				ArrayList<Chromosome> random_chromos = new ArrayList<Chromosome>();
				
				for(int j = 0; j < 10; j++)
				{ // gets 10 random, unique chromosomes between 1 and num_pop
					Random rand = new Random();
					int random = rand.nextInt(num_pop - 1) + 1; 
					Chromosome rand_chromo = population.get(random);
					
					for(Chromosome chromo : random_chromos)
					{ // might need to ensure uniqueness
						if (chromo.getPopNumber() == rand_chromo.getPopNumber())
						{
							random = rand.nextInt(num_pop - 1) + 1;
							rand_chromo = population.get(random);
						}
					}
					
					random_chromos.add(rand_chromo);
				}
				
				random_chromos = sortPopulation(random_chromos);
				ArrayList<Chromosome> best_four = new ArrayList<Chromosome>();
				
				for(int k = 0; k < 4; k++)
				{ // get the first ten, unaltered, in the next population
					random_chromos.get(k).setPopNumber(k);
					best_four.add(random_chromos.get(k));
				}
				
				Chromosome chromo_1 = best_four.get(0);
				Chromosome chromo_2 = best_four.get(1);
				
				Chromosome cross_prod = crossover(chromo_1, chromo_2);
				
				if(num_pop > 1000)
				{
					next_population.add(cross_prod);
					unique = true;
				}
				
				else
				{
					boolean skip = false;
					for (Chromosome chromo : next_population)
					{
						if (chromo.equal(cross_prod))
						{
							unique = false;
							skip = true;
							break;
						}
					}
					
					if(skip == false)
					{
						next_population.add(cross_prod);
						unique = true;
					}
				}
			}
		}
		
		next_population = sortPopulation(next_population);
		population = new ArrayList<Chromosome>(next_population);
	}
	
	public Chromosome crossover(Chromosome chromo_1, Chromosome chromo_2)
	{ // NOT DONE
		// choose a random crossover point
		Random rand = new Random();
		int crossover_point = rand.nextInt(this.getGeneLength() - 1) + 1; 
		
		List<Integer> chromo_genes = new ArrayList<Integer>();
		List<Integer> chromo_1_genes = new ArrayList<Integer>(chromo_1.getGenes());
		List<Integer> chromo_2_genes = new ArrayList<Integer>(chromo_2.getGenes());
		
		List<Integer> duplicates = new ArrayList<Integer>();
		
		for(int i = 0; i < crossover_point; i++)
		{
			chromo_genes.add(chromo_1_genes.get(i));
		}
		
		for(int i = crossover_point; i < gene_length; i++)
		{
			for (int gene : chromo_genes)
			{
				if (chromo_2_genes.get(i) == gene)
				{
					duplicates.add(gene);
				}
			}
			
			chromo_genes.add(chromo_2_genes.get(i));
		}
		
		int[] all_genes = new int[gene_length];
		for(int gene : original_chromo.getGenes())
		{ // just creating a list of all genes for referencing
			all_genes[gene - 1] = gene;
		}
		
		
		List<Integer> not_processed = new ArrayList<Integer>();
		
		for(int gene : chromo_genes)
		{ // cross off things that are in the sequence with a -1 and record duplicates
			for(int i = 0; i < gene_length; i++)
			{
				if (gene == all_genes[i])
				{
					all_genes[i] = -1;
				}
			}
		}
		
		for(int i = 0; i < gene_length; i++)
		{
			if (all_genes[i] != -1)
			{ // not processed
				not_processed.add(all_genes[i]);
			}
		}
		
		if(!duplicates.isEmpty())
		{
			for(int dup : duplicates)
			{ // change the remaining in array
				if(!not_processed.isEmpty())
				{
					int unprocessed = not_processed.remove(0);
					
					for(int i = 0; i < chromo_genes.size(); i++)
					{
						if (chromo_genes.get(i) == dup)
						{
							chromo_genes.set(i, unprocessed);
							break;
						}
					}
				}
			}
		}
		
		Chromosome chromo_prod = new Chromosome(chromo_genes, graph, start_key, 0);
		
		return chromo_prod;
	}
	
	private ArrayList<Chromosome> sortPopulation(ArrayList<Chromosome> array_list)
	{
		Comparator<Chromosome> compare = new Comparator<Chromosome>()
		{ // initialize PQ with the specified comparator
			public int compare(Chromosome c1, Chromosome c2)
			{
				return Double.compare(c1.getFitness() , c2.getFitness());
			}
		};
		
		PriorityQueue<Chromosome> pq = new PriorityQueue<Chromosome>(array_list.size() + 1, compare);
		
		for(Chromosome chromo : array_list)
		{
			pq.add(chromo);
		}
		
		array_list = new ArrayList<Chromosome>();
		
		int pop_number = 0;
		while(!pq.isEmpty())
		{ // dump all the sorted chromos out from the PQ and assign pop_num
			Chromosome chromo = pq.remove();
			chromo.setPopNumber(pop_number);
			array_list.add(chromo);
			pop_number++;
		}
		
		return array_list;
	}
	
	public void printBest()
	{
		System.out.print("Best Path (" + population.get(0).getFitness() + "): ");
		population.get(0).listGenes();
	}
	
	public void printPopulation()
	{	
		for(Chromosome chromo : population)
		{
			System.out.print(chromo.getPopNumber() + " (" + chromo.getFitness() + "): ");
			chromo.listGenes();
		}
	}
	
	public void printPopulation(ArrayList<Chromosome> array_list)
	{
		for(Chromosome chromo : array_list)
		{
			System.out.print(chromo.getPopNumber() + " (" + chromo.getFitness() + "): ");
			chromo.listGenes();
		}
	}
	
	private int getGeneLength()
	{
		int length = population.get(0).getNumGenes();
		return length;
	}
}

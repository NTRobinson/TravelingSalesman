package util;

import java.util.LinkedList;

public class Vertex<T>
{
	private T item;
	private String search_key; // need a search key (string) for each
	private boolean visited;
	private Vertex<T> previous;
	
	private double distance;
	
	// keeping track of all the edges that this vertex is a part of
	private LinkedList<Edge<T>> edges;
	private int number;
	
	public Vertex(T data, int vertex_number)
	{ // adds a vertex and sets the search key
		number = vertex_number;
		
		item = data;
		visited = false;
		search_key = (String) item;
		edges = new LinkedList<Edge<T>>();
		previous = null;
		
		distance = 0;
	}
	
	public void addEdge(Edge<T> edge)
	{ // adds to the list of connections that this vertex has
		if(!edges.contains(edge))
		{
			edges.add(edge);
		}
	}
	
	public String getKey()
	{
		return search_key;
	}
	
	public boolean setVisited(boolean input)
	{
		visited = input;
		return visited;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public boolean getVisited()
	{
		return visited;
	}
	
	public Vertex<T> getPrevious()
	{
		return previous;
	}
	public void setPrevious(Vertex<T> prev)
	{
		previous = prev;
	}
	
	public double getDistance()
	{
		return distance;
	}
	public void setDistance(double dist)
	{
		distance = dist;
	}
	
	public LinkedList<Edge<T>> getEdges()
	{
		return edges;
	}
	
	public void showEdges()
	{
		for (Edge<T> edge : edges)
		{
			System.out.print(edge.getWeight() + " ");
		}
	}
}

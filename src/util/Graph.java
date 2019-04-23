package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

// this class is basically our tree that we do on the test
public class Graph<T>
{
	private ArrayList<Vertex<T>> verts; // filled up with all verts read in
	// using the vertex edges as pseudo ArrayList<LinkedList<Vertex<T>>>, we don't actually need one knowing the connections
	private PriorityQueue<Edge<T>> pq;
	
	public Graph(int max_num_vertices)
	{
		verts = new ArrayList<Vertex<T>>();
		
		Comparator<Edge<T>> compare = new Comparator<Edge<T>>()
		{ // initialize PQ with the specified comparator
			public int compare(Edge<T> e1, Edge<T> e2)
			{
				return Double.compare(e1.getWeight(), e2.getWeight());
			}
		};
		
		pq = new PriorityQueue<Edge<T>>(max_num_vertices + 1, compare);
	}

	public void addVertex(T item)
	{ // allow items (items are identified by String search keys) to be stored in the Graph vertices
		Vertex<T> vert = new Vertex<T>(item, verts.size());
		verts.add(vert);
	}
	
	public void addEdge(String start_vertex_key, String end_vertex_key, double edge_weight)
	{ // add an edge between two vertices
		Vertex<T> start = getVertexByKey(start_vertex_key);
		Vertex<T> end = getVertexByKey(end_vertex_key);
		
		// construct edges
		Edge<T> edge_start = new Edge<T>(start, end, edge_weight);
		
		// add edge to vertex, results in all vertices hold any edges that touch
		start.addEdge(edge_start);
	}

	public void unvisitNodes()
	{ // resets all visit flags
		for(Vertex<T> vrt : verts)
		{
			vrt.setVisited(false);
		}
	}
	
	public void showAllEdges()
	{
		for (Vertex<T> vertex : verts)
		{
			System.out.println("\n" + vertex.getKey());
			vertex.showEdges();
		}
	}
	
	public void listVertices()
	{
		for (Vertex<T> vertex : verts)
		{
			System.out.println(vertex.getNumber() + " " + vertex.getKey());
		}
	}
	
	public int size()
	{
		return verts.size();
	}
	
	public ArrayList<Vertex<T>> getVertices()
	{
		return verts;
	}
	
	private Vertex<T> getVertexByKey(String key)
	{ // finds a vertex by name from our list
		for (Vertex<T> vrt : verts)
		{
			if (vrt.getKey().equals(key))
			{
				return vrt;
			}
		}
		return null;
	}
}
package util;

public class Edge<T>
{
	private Vertex<T> start;
	private Vertex<T> end;
	private double length;
	
	public Edge(Vertex<T> item1, Vertex<T> item2, double weight)
	{
		start = item1;
		end = item2;
		length = weight;
	}
	
	public boolean equals(Edge<T> other)
	{
		if (length == other.getWeight())
		{
			return true;
		}
			
		return false;
	}
	
	public String toString()
	{
		String output = "(" + start.getKey() +", " + end.getKey() + ", " + Double.toString(length) + ")";
		return output;
	}
	
	public double getWeight()
	{
		return length;
	}
	
	public Vertex<T> getStart()
	{
		return start;
	}
	
	public Vertex<T> getEnd()
	{
		return end;
	}
}
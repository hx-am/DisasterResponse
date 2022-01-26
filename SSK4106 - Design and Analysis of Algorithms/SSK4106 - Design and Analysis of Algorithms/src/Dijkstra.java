// Dijkstra

import java.util.ArrayList;

public class Dijkstra {
	
	private int vertexNum;
	private ArrayList<Integer> distances = new ArrayList<>();
	private ArrayList<String> paths = new ArrayList<>();
	private String[] nodes;
	
	/* method that does the implementation of Dijkstra's shortest path algorithm
	   for a graph that is being represented using the adjacency matrix representation */
	public Dijkstra(int graph[][], int source, String[] nodes) {
		this.vertexNum = graph.length;
		this.setNodes(nodes);
		// spSet[j] will be true if vertex j is included in the shortest path tree or the shortest distance from the source s to j is finalized
		Boolean spSet[] = new Boolean[vertexNum];
		// The output array distance[i] holds the shortest distance from source s to j/
		// Initializing all of the distances as INFINITE and spSet[] as false
		for (int j = 0; j < vertexNum; j++)
		{
			distances.add(Integer.MAX_VALUE);
			paths.add(nodes[source]);
			spSet[j] = false;
		}
					
		// Distance from the source vertex to itself is always 0
		distances.set(source, 0);
					
		// compute the shortest path for all the given vertices
		for (int cnt = 0; cnt < vertexNum - 1; cnt++)
		{
			// choose the minimum distance vertex from the set of vertices not yet processed
			// ux is always equal to source s in the first iteration.
			int ux = minimumDistance(distances, spSet);
			  
			// if the chosen vertex is marked as true, it means it is processed
			spSet[ux] = true;
			  
			// Updating the distance value of the neighboring vertices of the chosen vertex.
			for (int vx = 0; vx < vertexNum; vx++)
			{
				/* Update distance[vx] if and only if it is not in the spSet, there is an
				   edge from ux to vx, and the total weight of path from source s to
				   vx through ux is lesser than the current value of distance[vx] */
				if (!spSet[vx] && graph[ux][vx] != -1 && distances.get(ux) != Integer.MAX_VALUE && distances.get(ux) + graph[ux][vx] < distances.get(vx))
				{
					distances.set(vx, distances.get(ux) + graph[ux][vx]);
					paths.set(vx, paths.get(ux) + " -> " + nodes[vx]);
				}
			}
		}
	}
	
	public int minimumDistance(ArrayList<Integer> distance, Boolean spSet[]) {
		// Initialize min value
		int m = Integer.MAX_VALUE, m_index = -1;
		for (int vx = 0; vx < vertexNum; vx++)
		{
			if (spSet[vx] == false && distance.get(vx) <= m)
			{
				m = distance.get(vx);
				m_index = vx;
			}
		}
		return m_index;
	}
	
	// A utility method to display the built distance array
	public void printSolution(int distance[], int n) {
		System.out.println("The shortest Distance from source 0th node to all other nodes are: ");
		for (int j = 0; j < n; j++)
			System.out.println("To " + j + " the shortest distance is: " + distance[j]);
	}
	
	public ArrayList<Integer> getDistances() {
		return distances;
	}
	
	public ArrayList<String> getPath() {
		return paths;
	}

	public String[] getNodes() {
		return nodes;
	}

	public void setNodes(String[] nodes) {
		this.nodes = nodes;
	}
}

package ngordnet.main;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int V;
    public List<Integer>[] adj;
    public Graph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v ++) {
            adj[v] = new ArrayList<Integer>();
        }
    }
    // Directed Graph. Let v2 be a child of v1. But v1 isn't a child of v2
    public void addEdge(int v1, int v2) {
        adj[v1].add(v2);
    }
    public int V() {
        return V;
    }
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}

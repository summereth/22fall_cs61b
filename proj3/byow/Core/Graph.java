package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int V; // Number of Vertex
    public List<Integer>[] adj;
    public Graph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v ++) {
            adj[v] = new ArrayList<Integer>();
        }
    }
    // Undirected Graph
    public void addEdge(int v1, int v2) {
        adj[v1].add(v2);
        adj[v2].add(v1);
    }
    public int V() {
        return V;
    }
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
    public boolean isAdj(int a, int b) {
        for (int n : adj(a)) {
            if (n == b) {
                return true;
            }
        }
        return false;
    }
    /**
     * Traversal starting from a start point
     */
    public boolean isConnected() {
        boolean[] marked = new boolean[V];
        dfsHelper(0, marked, false);
        for (boolean n : marked) {
            if (!n) {
                return false;
            }
        }
        return true;
    }
    public void dfsHelper(int v, boolean[] marked, boolean toPrint) {
        marked[v] = true;
        if (toPrint) {
            System.out.println(v);
        }
        for (int i : adj(v)) {
            if (!marked[i]) {
                if (toPrint) {
                    System.out.print("  ->");
                }
                dfsHelper(i, marked, toPrint);
            }
        }
    }
    public void printAdj() {
        for (int i = 0; i < V; i ++) {
            System.out.println(i + ": " + adj(i));
        }
    }
    /**
     * To test isConnected()
     */
    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        System.out.println(g.isConnected());
        g.printAdj();
        System.out.println(g.isAdj(0,1));
        System.out.println(g.isAdj(0,3));
    }
}

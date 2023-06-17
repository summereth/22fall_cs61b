package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.mockito.internal.util.collections.Sets;

import java.util.*;

public class Percolation {
    public int size;
    public int[][] sites;
    private WeightedQuickUnionUF u;
    private WeightedQuickUnionUF antiBackwashU;
    public int openSites;
    private int TOP;
    private int BOTTOM;
    private List<Integer> openBottomSites;
    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        TOP = N * N;
        BOTTOM = N * N + 1;
        openSites = 0;
        openBottomSites = new ArrayList<>();
        sites = new int[size][size];
        u = new WeightedQuickUnionUF(size * size + 2);
        antiBackwashU = new WeightedQuickUnionUF(size * size + 1); // create another sets of union w/o BOTTOM
    }
    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        checkArgument(row, col);
        sites[row][col] = 1;
        List<Integer> neighbors = getOpenNeighbors(row, col);
        // connect with TOP/BOTTOM if it's in the 1st/last row
        if (row == 0) {
            u.union(TOP, xyTo1D(row, col));
            antiBackwashU.union(TOP, xyTo1D(row, col));
        } else if (row == size - 1) {
            u.union(BOTTOM, xyTo1D(row, col));
        }
        openSites += 1;
        // connect with open neighbors
        for (int n : neighbors) {
            u.union(n, xyTo1D(row, col));
            antiBackwashU.union(n, xyTo1D(row, col));
        }
    }
    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        checkArgument(row, col);
        return (sites[row][col] == 1);
    }
    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        checkArgument(row, col);
        if (antiBackwashU.connected(TOP, xyTo1D(row, col))) {
            return true;
        }
        return false;
    }
    public int numberOfOpenSites() {
        // number of open sites
        return openSites;
    }
    public boolean percolates() {
        // does the system percolate?
        return u.connected(TOP, BOTTOM);
    }
    private void checkArgument(int row, int col) {
        boolean legalArgument = (row >= 0 && row <= size - 1 &&
                col >= 0 && col <= size - 1);
        if (!legalArgument) {
            throw new IndexOutOfBoundsException();
        }
    }
    private int xyTo1D(int x, int y) {
        // return a 1-digit identifier for (row, col)
        return x * size + y;
    }
    private int[] toXY(int n){
        int[] result = new int[2];
        result[0] = (int)(n / size);
        result[1] = (int)(n % size);
        return result;
    }
    private List<Integer> getOpenNeighbors(int row, int col) {
        int curr = xyTo1D(row, col);
        List<Integer> neighbors = new LinkedList<>();
        neighbors.add(curr - 1);
        neighbors.add(curr + 1);
        neighbors.add(curr - size);
        neighbors.add(curr + size);
        if (row == 0) {
            neighbors.remove(2);
        }
        if (row == size - 1) {
            neighbors.remove(3);
        }
        if (col == 0) {
            neighbors.remove(0);
        }
        if (col == size - 1) {
            neighbors.remove(1);
        }
        Iterator<Integer> itr = neighbors.iterator();
        while (itr.hasNext()) {
            int n = itr.next();
            if (!isOpen(toXY(n)[0], toXY(n)[1])) {
                itr.remove();
            }
        }
        return neighbors;
    }
    private void printSites() {
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j ++) {
                System.out.print(sites[i][j]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(3,2);
        p.open(0,0);
        p.open(1,0);
        p.open(2,0);
        p.open(2,1);
        p.open(2,2);
        p.printSites();
        System.out.println(p.isOpen(3,2));
        System.out.println(p.isFull(3,2));
        System.out.println(p.numberOfOpenSites());
        p.open(4,2);
        System.out.println(p.percolates());
    }
}

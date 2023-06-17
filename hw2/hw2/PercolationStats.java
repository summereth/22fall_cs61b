package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] thresholds;
    private double CONST = 1.96;
    private double times;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[T];
        times = T;
        for (int i = 0; i < T; i ++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int randRow = StdRandom.uniform(N);
                int randCol = StdRandom.uniform(N);
                if (p.isOpen(randRow, randCol)) {
                    continue;
                }
                p.open(randRow, randCol);
            }
            thresholds[i] = (double) (p.openSites) / (double) (N * N);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(thresholds);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholds);
    }
    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        double low = mean() - ((CONST * stddev()) / Math.sqrt(times));
        return low;
    }
    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        double high = mean() + ((CONST * stddev()) / Math.sqrt(times));
        return high;
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        for (int n = 20; n < 200; n += 10) {
            Stopwatch timer1 = new Stopwatch();
            PercolationStats ps = new PercolationStats(n, 30, pf);
            double time1 = timer1.elapsedTime();
            System.out.println("Mean = " + ps.mean());
            //System.out.println("StdDev = " + ps.stddev());
            //System.out.println("ConfidenceLow = " + ps.confidenceLow());
            //System.out.println("ConfidenceHigh = " + ps.confidenceHigh());
            System.out.println("Running time of N: " + n + ", T: 30, is " + time1);
        }
        for (int t = 30; t < 100; t += 10) {
            Stopwatch timer2 = new Stopwatch();
            PercolationStats ps = new PercolationStats(20, t, pf);
            double time2 = timer2.elapsedTime();
            System.out.println("Mean = " + ps.mean());
            //System.out.println("StdDev = " + ps.stddev());
            //System.out.println("ConfidenceLow = " + ps.confidenceLow());
            //System.out.println("ConfidenceHigh = " + ps.confidenceHigh());
            System.out.println("Running time of N: 20, T: " + t + ", is " + time2);
        }
    }
}

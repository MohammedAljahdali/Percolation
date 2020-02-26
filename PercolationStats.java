/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] openSites;
    private final int trails;
    private final double  const196 = 1.96;
    public PercolationStats(int n, int trials) {
        this.trails = trials;

        int totalSites = n*n;
        Percolation[] percs = new Percolation[trials];
        for (int i = 0; i < trials; i++) {
            percs[i] = new Percolation(n);
            while (!percs[i].percolates()) {
                int randomNumber = StdRandom.uniform((n * n) - 1);
                int col = randomNumber % n;
                int row = (randomNumber - col) / n;
                if(!percs[i].isOpen(row + 1, col + 1)) {
                    percs[i].open(row + 1, col + 1);
                }
            }
        }
        openSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            openSites[i] = (double)percs[i].numberOfOpenSites() / totalSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((stddev() * const196 ) / Math.sqrt(trails));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return mean() - ((stddev() * const196) / Math.sqrt(trails));
    }
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        // int t = Integer.parseInt(args[1]);
        int n = 200, t = 100;
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println("mean\t= "+percolationStats.mean());
        System.out.println("stddev\t= "+percolationStats.stddev());
        System.out.println("95%% confidence interval\t= ["+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi()+"]");
    }
}

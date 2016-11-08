import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int trials;
    private int gridSize;
    private double[] results;
    private double[] timeResults;
    private Stopwatch stopwatch;

    public PercolationStats(int gridSize, int trials) {
        this.gridSize = gridSize;
        this.trials = trials;
        runTest();
    }

    private void runTest() {
        results = new double[trials];
        timeResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            stopwatch = new Stopwatch();
            int openSites = openSitesTillPercThenGetSiteCount();
            timeResults[i] = stopwatch.elapsedTime();
            results[i] = (double) openSites / (double) (gridSize * gridSize);
        }
        postTestAnalytics();
    }

    private int openSitesTillPercThenGetSiteCount() {
        Percolation perc = new Percolation(gridSize);
        int openSites = 0;
        stopwatch = new Stopwatch();
        while (!perc.percolates()) {
            int row = StdRandom.uniform(0, gridSize);
            int col = StdRandom.uniform(0, gridSize);
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
                openSites++;
            }
        }
        return openSites;
    }

    private void postTestAnalytics() {
        System.out.println("The grid size being tested is: " + gridSize + " X " + gridSize);
        System.out.println("The number of trials being ran: " + trials);
        System.out.println("The average time a test took to run: " + (float) StdStats.mean(timeResults) + " seconds");
        System.out.println("The average threshold till it perculated: " + (float) StdStats.mean(results));
    }

    public static void main(String[] args) {
        PercolationStats pStats = new PercolationStats(10, 1000);
    }
}
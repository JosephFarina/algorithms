import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PercolationStats {
    private int trials;
    private int gridSize;
    List<Integer> trialRecords = new ArrayList<Integer>();
    private Percolation perc;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.gridSize = n;
    }

    private void runAllTests() {
        for (int i = 0; i < trials; i++) {
            singlePercolate();
        }
        System.out.print(trialRecords);
    }

    private void singlePercolate() {
        perc = new Percolation(gridSize);
        int row, col, count = 0;
        while(!perc.percolates()) {
            Random rand = new Random();
            row = rand.nextInt(gridSize);
            col = rand.nextInt(gridSize);
            perc.open(row, col);
            count++;

        }
        int percentCompleted = count / (gridSize * gridSize);
        System.out.println(count + " " + (double) count / (gridSize * gridSize));
        trialRecords.add(percentCompleted);
    }

    // sample mean of percolation threshold
    public double mean(){
        return 0.0;
    }
    // sample standard deviation of percolation threshold
    public double stddev()  {
        return 0.0;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi()  {
        return 0.0;
    }
    // test client (described below)
    public static void main(String[] args)  {
        PercolationStats p = new PercolationStats(20, 50000);
        p.runAllTests();
    }
}
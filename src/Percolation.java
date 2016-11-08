import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;

public class Percolation {
    private int gridSize;
    private boolean[][] grid;
    private int topVertex;
    private int bottomVertex;
    private WeightedQuickUnionUF quickUnion;

    public Percolation(int gridSize) throws IllegalArgumentException {
        if (gridSize <= 0) throw new IllegalArgumentException("Grid size must be bigger than 0");
        this.gridSize = gridSize;
        grid = new boolean[gridSize][gridSize];
        topVertex = 0;
        bottomVertex = gridSize * gridSize + 1;
        quickUnion = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        for (int i = 0; i < gridSize; i++) {
            quickUnion.union(topVertex, colRowTo1d(0, i));
            quickUnion.union(bottomVertex, colRowTo1d(grid.length - 1, i));
        }
    }

    public void open(int row, int col) {
        validateError(row, col);
        if (!isOpen(row, col)) {
            int newlyOpenedVal = colRowTo1d(row, col);
            grid[row][col] = true;

            // top
            if (row > 0 && grid[row - 1][col]) {
                int upVal = colRowTo1d(row - 1, col);
                quickUnion.union(newlyOpenedVal, upVal);
            }

            // right
            if (col < grid.length - 1 && grid[row][col + 1]) {
                int rightVal = colRowTo1d(row, col + 1);
                quickUnion.union(newlyOpenedVal, rightVal);
            }

            //bottom
            if (row < grid.length - 1 && grid[row + 1][col]) {
                int bottomVal = colRowTo1d(row + 1, col);
                quickUnion.union(newlyOpenedVal, bottomVal);
            }

            //left
            if (col > 0 && grid[row][col - 1]) {
                int leftVal = colRowTo1d(row, col - 1);
                quickUnion.union(newlyOpenedVal, leftVal);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateError(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        validateError(row, col);
        return quickUnion.connected(topVertex, colRowTo1d(row, col)) && isOpen(row, col);
    }

    public boolean percolates() {
        return quickUnion.connected(topVertex, bottomVertex);
    }

    // Private

    private void prettyPrintGrid() {
        for (boolean[] arr : this.grid) {
            System.out.println(Arrays.toString(arr));
        }
    }

    private int colRowTo1d(int row, int col) {
        return (row * this.gridSize) + col + 1;
    }

    private void validateError(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Row and Col need to be great than 0.. row: " + row + " col: " + col);
        } else if (row > this.gridSize - 1 || col > this.gridSize - 1) {
            throw new IndexOutOfBoundsException("Row and call need to be less than " + this.gridSize);
        }
    }
}


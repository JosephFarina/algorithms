import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private Site[][] grid;
    private int gridSize;
    private int topVertex = 0;
    private int bottomVertex;
    private WeightedQuickUnionUF weightedQuickUnion;

    public static void main(String[] args)  {

    }

    public Percolation(int gridSize) throws IllegalArgumentException {
        if (gridSize <= 0) throw new IllegalArgumentException();
        weightedQuickUnion = new WeightedQuickUnionUF((gridSize * gridSize) + 2);
        this.gridSize = gridSize;
        bottomVertex = gridSize * gridSize + 1;
        grid = new Site[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = Site.blocked;
            }
        }
    }

    public void open(int row, int col) {
        validateError(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = Site.open;
            connectToOpenNeighbors(row, col);

            if (row == 0) {
                weightedQuickUnion.union(topVertex, extractFromGrid(row, col));
            }

            else if (row == gridSize - 1) {
                weightedQuickUnion.union(bottomVertex, extractFromGrid(row, col));
            }
        }
    }

    private void connectToOpenNeighbors(int row, int col) {
        for (Direction dir: Direction.values()) {
            connectToNeighbor(row, col, dir);
        }
    }

    private void connectToNeighbor(int row, int col, Direction direction) {
        int connector = extractFromGrid(row, col);
        int[] neighbor = getNeighborByDirection(row, col, direction);
        int extractedNeighbor = extractFromGrid(neighbor[0], neighbor[1]);
        if (valid(row, col) && isOpen(neighbor[0], neighbor[1]) && !weightedQuickUnion.connected(connector, extractedNeighbor)) {
            weightedQuickUnion.union(connector, extractedNeighbor);
        }
    }

    private int[] getNeighborByDirection(int row, int col, Direction direction) throws IllegalArgumentException {
        int[] neighbor = new int[2];

        switch (direction) {
            case up:
                neighbor[0] = row + 1;
                neighbor[1] = col;
                break;
            case right:
                neighbor[0] = row;
                neighbor[1] = col + 1;
                break;
            case down:
                neighbor[0] = row - 1;
                neighbor[1] = col;
                break;
            case left:
                neighbor[0] = row;
                neighbor[1] = col - 1;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return neighbor;
    }

    public boolean isOpen(int row, int col) {
        validateError(row, col);
        return grid[row][col] == Site.open;
    }


    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int cell = extractFromGrid(row, col);
        return connectsToTop(cell) || connectsToBottom(cell);
    }

    private boolean connectsToTop(int cell) {
        return weightedQuickUnion.connected(cell, topVertex);
    }

    private boolean connectsToBottom(int cell) {
        return weightedQuickUnion.connected(cell, bottomVertex);
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnion.connected(topVertex, bottomVertex);
    }

    private int extractFromGrid(int row, int col) {
        return (row * this.gridSize) + col + 1;
    }

    private boolean valid(int row, int col) {
        return (row > 0 && col > 0 && row < this.gridSize - 1 && col < this.gridSize - 1);
    }

    private void validateError(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Row and Col need to be great than 0.. row: "  + row + " col: " + col);
        }

        else if (row > this.gridSize - 1  || col > this.gridSize - 1 ) {
            throw new IndexOutOfBoundsException("Row and call need to be less than " + this.gridSize);
        }
    }
}


enum Site {
    blocked, open
}

enum Direction {
    up, right, down, left
}
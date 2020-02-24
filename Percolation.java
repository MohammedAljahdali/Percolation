/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    private int[][] grid;
    private int[][] size;
    private boolean[][] site;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        grid = new int[n][n];
        size = new int[n][n];
        site = new boolean[n][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = (i*grid.length)+j;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > grid.length-1 || row < 1 || col > grid.length-1 || col < 1 )
            throw new IllegalArgumentException();
        if (site[row][col] == true)
            return;
        else site[row][col] = true;
        if (row != 0 && site[row-1][col]) {
            if (size[row-1][col] < size[row][col]) {
                grid[row - 1][col] = grid[row][col];
                size[row][col]++;
            } else {
                grid[row][col] = grid[row - 1][col];
                size[row - 1][col]++;
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > grid.length-1 || row < 1 || col > grid.length-1 || col < 1 )
            throw new IllegalArgumentException();
        // return grid[row][col] != (row*grid.length)+col || size[row][col] > 0;
        return site[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > grid.length-1 || row < 1 || col > grid.length-1 || col < 1 )
            throw new IllegalArgumentException();
        return grid[row][col] == (row*grid.length)+col && size[row][col] == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return 0;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    private void root(int row, int col) {
        int root = grid[row][col];
        while (root != (row*grid.length)+col) {
            col = root%grid.length;
            row = (root-col)/grid.length;
            root = grid[row][col];
        }
    }
    private void connect(int row, int col, int nextRow, int nextCol) {

    }

    public static void main(String[] args) {
        // Percolation x = new Percolation(5);
        // x.open(4,3);
        // int[][] grid = x.grid;
        //
        // for (int i = 0, counter = 0; i < grid.length; i++) {
        //     for (int j = 0; j < grid.length; j++) {
        //         System.out.print(x.site[i][j]+" ");
        //     }
        //     System.out.println();
        // }
        System.out.println(19%5);
    }
}

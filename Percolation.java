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
                grid[i][j] = (i * grid.length) + j;
                size[i][j] = 1;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row -= 1; col -= 1;
        if (row > grid.length - 1 || row < 0 || col > grid.length - 1 || col < 0)
            throw new IllegalArgumentException();
        if (site[row][col] == true)
            return;
        else
            site[row][col] = true;
        if (row != 0 && site[row - 1][col])
            connect(row, col, row - 1, col);
        if (row != grid.length - 1 && site[row + 1][col])
            connect(row, col, row + 1, col);
        if (col != 0 && site[row][col-1])
            connect(row, col, row, col - 1);
        if (col != grid.length - 1 && site[row][col + 1])
            connect(row, col, row, col + 1);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row -= 1; col -= 1;
        if (row > grid.length - 1 || row < 0 || col > grid.length - 1 || col < 0)
            throw new IllegalArgumentException();
        // return grid[row][col] != (row*grid.length)+col || size[row][col] > 0;
        return site[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row -= 1; col -= 1;
        if (row > grid.length - 1 || row < 0 || col > grid.length - 1 || col < 0)
            throw new IllegalArgumentException();
        // if (!isOpen(row,col))
        //     return false;
        int[][] topVirtualSites = connect(0);
        int[][] bottomVirtualSites = connect(grid.length - 1);
        for (int i = 0; i < grid.length; i++) {
            int[] r1Indecies = root(topVirtualSites[i][0], topVirtualSites[i][1]);
            int[] r2Indecies = root(row, col);
            int r1 = grid[r1Indecies[0]][r1Indecies[1]];
            int r2 = grid[r2Indecies[0]][r2Indecies[1]];
            if (r1 == r2) {
                for (int j = 0; j < grid.length; j++) {
                    int[] r3Indecies = root(bottomVirtualSites[j][0], bottomVirtualSites[j][1]);
                    int r3 = grid[r3Indecies[0]][r3Indecies[1]];
                    if(r3 == r2)
                        return true;
                }
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (isOpen(i+1, j + 1))
                    counter++;
            }
        }
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        int[][] topVirtualSites = connect(0);
        int[][] bottomVirtualSites = connect(grid.length-1);
        for (int i = 0; i < grid.length; i++) {
            int[] r1Indecies = root(topVirtualSites[i][0], topVirtualSites[i][1]);
            int r1 = grid[r1Indecies[0]][r1Indecies[1]];
                for (int j = 0; j < grid.length; j++) {
                    int[] r3Indecies = root(bottomVirtualSites[j][0], bottomVirtualSites[j][1]);
                    int r3 = grid[r3Indecies[0]][r3Indecies[1]];
                    if(r3 == r1)
                        return true;
                }
        }
        return false;
    }

    private int[] root(int row, int col) {
        int root = grid[row][col];
        int newCol = col;
        int newRow = row;
        while (root != (newRow * grid.length) + newCol) {
            newCol = root % grid.length;
            newRow = (root - newCol) / grid.length;
            grid[row][col] = grid[newRow][newCol];
            root = grid[newRow][newCol];
        }
        int[] rootIndex = {newRow, newCol};
        return rootIndex;
    }
    private void connect(int row, int col, int nextRow, int nextCol) {
        int[] indexOfRoot1 = root(row, col);
        int[] indexOfRoot2 = root(nextRow, nextCol);
        if (grid[indexOfRoot1[0]][indexOfRoot1[1]] == grid[indexOfRoot2[0]][indexOfRoot2[1]])
            return;
        if (size[indexOfRoot1[0]][indexOfRoot1[1]] < size[indexOfRoot2[0]][indexOfRoot2[1]]) {
            grid[indexOfRoot1[0]][indexOfRoot1[1]] = grid[indexOfRoot2[0]][indexOfRoot2[1]];
            size[indexOfRoot2[0]][indexOfRoot2[1]] += size[indexOfRoot1[0]][indexOfRoot1[1]];
        } else {
            grid[indexOfRoot2[0]][indexOfRoot2[1]] = grid[indexOfRoot1[0]][indexOfRoot1[1]];
            size[indexOfRoot1[0]][indexOfRoot1[1]] +=  size[indexOfRoot2[0]][indexOfRoot2[1]];
        }
    }
    private int[][] connect(int row) {
        int[][] virtualSite = new int[grid.length][2];
        for (int i = 0; i < grid.length; i++) {
            int[] indexOfRoot = root(row,i);
            virtualSite[i][0] = indexOfRoot[0];
            virtualSite[i][1] = indexOfRoot[1];
        }
        return virtualSite;
    }

    public static void main(String[] args) {
    }
}

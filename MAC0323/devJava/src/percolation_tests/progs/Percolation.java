/**
 * Created by juliano on 4/5/17.
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF uf;
    // 0 - Closed , 1 - Open
    private int [][] grid;
    private int n, virt1, virt2;
    private int openSites;



    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        virt1 = n*n;
        virt2 = n*n + 1;
        grid = new int[n][n];
        // Creates the grid plus 2 virtual sites: n² + 1 and n² + 2
        uf = new WeightedQuickUnionUF(n*n + 2);
    }

    private boolean outOfRange(int row, int col) {
        return (row < 0 || col < 0 || row >= n || col >= n);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(outOfRange(row, col))
            throw new java.lang.NullPointerException("Site is out of range! ["+row+","+col+"]");
        if(isOpen(row, col)) return;
        if (row == 0)
            uf.union(this.virt1, row*n + col);
        else if (row == n - 1)
            uf.union(this.virt2, row*n + col);

        int[][] sides = new int[][] {
                { row - 1, col },
                { row + 1, col },
                { row, col - 1 },
                { row, col + 1 }
        };
        grid[row][col] = 1;
        openSites++;
        // Check if all neighboors are open and connect if they are
        for (int i = 0; i < 4; i++)
            if(!outOfRange(sides[i][0], sides[i][1]) && isOpen(sides[i][0], sides[i][1]))
                uf.union(row * n + col, sides[i][0] * n + sides[i][1]);

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(outOfRange(row, col))
            throw new java.lang.NullPointerException("Site is out of range!");
        return uf.connected(row*n + col, this.virt1);
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col] == 1;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(this.virt1, this.virt2);
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(10);

    }  // unit testing (required)

}

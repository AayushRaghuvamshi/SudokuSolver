package com.company;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    public static void main(String[] args) {
	// write your code here
        int board[][] = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
                {5, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 8, 7, 0, 0, 0, 0, 3, 1},
                {0, 0, 3, 0, 1, 0, 0, 8, 0},
                {9, 0, 0, 8, 6, 3, 0, 0, 5},
                {0, 5, 0, 0, 9, 0, 6, 0, 0},
                {1, 3, 0, 0, 0, 0, 2, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 7, 4},
                {0, 0, 5, 2, 0, 6, 3, 0, 0}};

        System.out.println("Unsolved");
        printSudoku(board);
        solveSudoku(board);
        System.out.println("\n");
        System.out.println("Solved");
        printSudoku(board);
    }

    public static boolean isValidBoard(int b[][], PositionPair p, int n) {

        for(int col = 0; col < b[0].length; ++col) {            //checks that number isn't already in the row
            if (b[p.getX()][col] == n && col != p.getY()) {
                return false;
            }
        }

        for(int row = 0; row < b.length; ++row) {
            if (b[row][p.getY()] == n && row != p.getX()) {            //checks that number isn't already in the column
                return false;
            }
        }

        // label the 9 sub-grids by separate coordinates, so left-most box is (0,0), immediately to the right
        // is (0,1), then (0,2), then we go down one level, left-most is (1,0), then (1,1) and so on. We can get
        // these sub-grid coordinates by dividing by 3, and we'll use them by multiplying by three to get the starting index
        // within that sub-grid.

        int subgrid_x = p.getX() / 3;
        int subgrid_y = p.getY() / 3;

        for (int x = 3 * subgrid_x; x < 3 + 3 * subgrid_x; ++x) {
            for (int y = 3 * subgrid_y; y < 3 + 3 * subgrid_y; ++y) {
                if (b[x][y] == n && x != p.getX() && y!= p.getY()) {            //checks that number isn't already in the sub-grid
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] b) {
        PositionPair p = findFirstEmpty(b);
        if (p == null) {
            return true;
        }
        for (int n = 1; n <= 9; ++n) {
            if (isValidBoard(b, p, n)) {
                b[p.getX()][p.getY()] = n;
                if (solveSudoku(b)) {
                    return true;
                }
                b[p.getX()][p.getY()] = 0;
            }
        }
        return false;
    }

    public static PositionPair findFirstEmpty(int[][] b) {
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 9; ++y) {
                if (b[x][y] == 0) {
                    return new PositionPair(x, y);
                }
            }
        }
        return null;
    }

    public static void printSudoku(int[][] b) {
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("---------------------");
            }
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                if (j==8) {
                    System.out.println(b[i][j]);
                }
                else {
                    System.out.print(b[i][j] + " ");
                }
            }
        }
    }

}

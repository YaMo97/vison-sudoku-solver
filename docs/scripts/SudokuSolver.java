interface SudokuSolver {
    boolean solveSudoku(int[][] board, int i, int j);

    boolean isValid(int[][] board, int x, int y, int input);
}
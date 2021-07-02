public class SudokuSolverImpl implements SudokuSolver {

    public boolean solveSudoku(int[][] board, int i, int j) {
        if (i >= board.length)
            return true;

        int next_i = i;
        int next_j = j + 1;

        if ( next_j >= board[0].length ) {
            next_j = 0;
            next_i++;
        }

        // System.out.print(i + " " + j + "; ");
        // System.out.println(next_i + " " + next_j);
        
        boolean isSolved = false;

        if ( board [i][j] != 0) {
            return solveSudoku(board, next_i, next_j);
        } else {
            // Current box is empty
            // Try all possible options and check if they are valid
            for (int option = 1; option <= 9; option++) {
                if ( isValid(board, i, j, option) == true ) {
                    // Option is valid
                    board[i][j] = option;

                    isSolved = solveSudoku(board, next_i, next_j);

                    if ( isSolved == false )
                        board[i][j] = 0;
                } 
            }
        }

        return isSolved;
    }

    public boolean isValid(int[][] board, int x, int y, int input) {
        // Check in rows
        for (int j = 0; j < board[x].length; j++) {
            if ( board[x][j] == input ) 
                return false;
        }

        // Check in columns
        for (int i = 0; i < board.length; i++) {
            if ( board[i][y] == input ) 
                return false;
        }

        // Check in sub-matrix
        int subMatrix_i = ( x / 3 ) * 3;
        int subMatrix_j = ( y / 3 ) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ( board[subMatrix_i + i][subMatrix_j + j] == input) 
                    return false;
            }
        }

        // All tests passed
        return true;
    }
}
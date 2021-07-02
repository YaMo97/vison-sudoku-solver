class SudokuBoardImpl implements SudokuBoard {

    SudokuSolver mSudokuSolver;

    int[][] board = new int[9][9];

    public SudokuBoardImpl(SudokuSolver solver) {
        this.mSudokuSolver = solver;
    }

    public void inputBoard() {
        System.out.println("Please input the grid.");

        // int[][] temp = {
        //     { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        //     { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
        //     { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
        //     { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        //     { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
        //     { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
        //     { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        //     { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
        //     { 7, 8, 9, 1, 2, 3, 4, 5, 6 }
        // };
         // int[][] temp = {
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
    
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
            
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 },
        //     { 0, 0, 0,   0, 0, 0,   0, 0, 0 }
        // };  

        int[][] temp = {
            { 0, 2, 0,   0, 0, 4,   3, 0, 0 },
            { 9, 0, 0,   0, 2, 0,   0, 0, 8 },
            { 0, 0, 0,   6, 0, 9,   0, 5, 0 },
    
            { 0, 0, 0,   0, 0, 0,   0, 0, 1 },
            { 0, 7, 2,   5, 0, 3,   6, 8, 0 },
            { 6, 0, 0,   0, 0, 0,   0, 0, 0 },

            { 0, 8, 0,   2, 0, 5,   0, 0, 0 },    
            { 1, 0, 0,   0, 9, 0,   0, 0, 3 },
            { 0, 0, 9,   8, 0, 0,   0, 6, 0 }
        };  

        board = temp;
    }

    public boolean isBoardValid() {

        // Check if input board is valid or not.

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                int currentItem = board[x][y];
                // check if the current item is in the remaining board.

                if (currentItem == 0) 
                    continue;

                // Check in rows
                for (int j = 0; j < board[x].length; j++) {
                    if ( j != y && board[x][j] == currentItem ) 
                        return false;
                }

                // Check in columns
                for (int i = 0; i < board.length; i++) {
                    if ( i != x && board[i][y] == currentItem ) 
                        return false;
                }

                // Check in sub-matrix
                int subMatrix_i = ( x / 3 ) * 3;
                int subMatrix_j = ( y / 3 ) * 3;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if ( subMatrix_i + i != x && subMatrix_j + j != y && board[subMatrix_i + i][subMatrix_j + j] == currentItem) 
                            return false;
                    }
                }

            }
        }

        // All tests passed
        return true;
    }
    
    public void displayBoard() {
        System.out.println("Board => ");

        char[][] template = {
            { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '-', '-', '-', '+', '-', '-', '-', '+', '-', '-', '-', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '-', '-', '-', '+', '-', '-', '-', '+', '-', '-', '-', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '|', '0', '0', '0', '|', '0', '0', '0', '|', '0', '0', '0', '|' },
            { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
        };

        // Fill in the template and print the template.

        int board_i = 0; 
        int board_j = 0;

        for (int i = 0; i < template.length; i++) {
            for (int j = 0; j < template[i].length - 1; j++) {
                if (i % 4 == 0) {
                    System.out.print(template[i][j] + " ");
                } 
                else if (j % 4 == 0)
                    System.out.print(template[i][j] + " ");
                else {
                    System.out.print(board[board_i][board_j] + " ");
                    board_j++;
                }
            }

            // Print last element without seperator and with new line
            System.out.println( template[i][template[i].length - 1] );

            if (i % 4 != 0) {
                board_i++;
                board_j = 0;
            }
        }
    }

    public boolean solveBoard() {
        return mSudokuSolver.solveSudoku(board, 0, 0);
    }

    public void displaySolution() {
        displayBoard();
    }
}
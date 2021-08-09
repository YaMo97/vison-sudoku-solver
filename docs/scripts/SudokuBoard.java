interface SudokuBoard {
    void inputBoard();

    boolean isBoardValid();
    
    void displayBoard();

    boolean solveBoard();

    void displaySolution();
}
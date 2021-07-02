import java.util.*;

class SudokuSolverScript {

    private static SudokuBoard mSudokuBoard;

    public static void main(String[] args) {
        System.out.println("=========================");
        System.out.println("Welcome to Sudoku Solver!");
        System.out.println("=========================");
        System.out.println();

        mSudokuBoard = new SudokuBoardImpl(new SudokuSolverImpl());

        // Take Input
        mSudokuBoard.inputBoard();

        mSudokuBoard.displayBoard();

        // Validate Input
        if (mSudokuBoard.isBoardValid() != true) {
            System.out.println("Invalid Board input!");
            return;
        }
        
        System.out.println("Input Board is valid!");

        // Call SudokuSolver

        if ( mSudokuBoard.solveBoard() == true ) {
            System.out.println("Solution Found!");
            mSudokuBoard.displaySolution();
        } else {
            System.out.println("No Solution Found!");
        }
        
        // // Wait for User Input.
        // waitForEnter();
    }

    private static void waitForEnter() {
        System.out.println("Press ENTER to continue..");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
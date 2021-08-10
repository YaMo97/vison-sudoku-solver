package io.github.yamo97.sudokusolver.ui.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.yamo97.sudokusolver.di.sudokuRepository
import io.github.yamo97.sudokusolver.model.Cell
import io.github.yamo97.sudokusolver.model.GameState
import io.github.yamo97.sudokusolver.model.Puzzle
import io.github.yamo97.sudokusolver.model.SudokuGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel: ViewModel() {
    private val TAG = GameViewModel::class.simpleName

    var puzzleID: Int = -1
    var puzzle: Puzzle? = null

    var sudokuGame : SudokuGame? = null

    val gameStatusLiveData = MutableLiveData<GameState>()

    fun loadAndStartGame() {
        viewModelScope.launch {
            try {
                gameStatusLiveData.value = GameState.LOADING

                puzzle = loadPuzzle() ?: throw Exception("Could not load puzzle.")

                // Puzzle Loaded
                // Create Sudoku Game from Puzzle
                sudokuGame = SudokuGame(puzzle!!) ?: throw Exception("Could not start sudoku game.")

                gameStatusLiveData.value = GameState.PLAYING
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                gameStatusLiveData.value = GameState.ERROR
            }
        }
    }

    private suspend fun loadPuzzle(): Puzzle? {
        return if (puzzleID > -1) sudokuRepository.getPuzzleByID(puzzleID) else null
    }

    /**
     * Checked Everytime an input is received.
     *
     * Can send completion signal multiple times.
     */
    fun checkForCompletion(cells: List<Cell>) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // If All cells are filled
                if ( cells.contains(Cell(0, 0, 0)) ) {
                    // Not completed
                } else {
                    // completed
                    gameStatusLiveData.postValue(GameState.COMPLETED)
                }
            }
        }
    }

    fun checkSolution() {

    }
}
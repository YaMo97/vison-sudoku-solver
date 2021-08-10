package io.github.yamo97.sudokusolver.data.repo

import io.github.yamo97.sudokusolver.model.Board
import io.github.yamo97.sudokusolver.model.Puzzle
import io.github.yamo97.sudokusolver.model.PuzzleDifficulty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SudokuRepository {

    suspend fun getPuzzles(): List<Puzzle> {

        return withContext(Dispatchers.Default) {
            return@withContext getMockPuzzlesList()
        }
    }

    suspend fun getPuzzleByID(puzzleID: Int) : Puzzle {

        return withContext(Dispatchers.IO) {
            delay(5000)
            return@withContext getMockPuzzle(puzzleID)
        }
    }




    private fun getMockPuzzlesList() : List<Puzzle> {

        val board = Board.createMockBoard()
        return List(100) { i -> Puzzle(i, i,  board, listOf(), PuzzleDifficulty.EASY, false) }
    }

    private fun getMockPuzzle(puzzleID: Int) : Puzzle {
        return Puzzle(puzzleID, 1,  Board.createMockBoard(), listOf(), PuzzleDifficulty.EASY, false)
    }
}
package io.github.yamo97.sudokusolver.data.repo

import io.github.yamo97.sudokusolver.model.Board
import io.github.yamo97.sudokusolver.model.Puzzle
import io.github.yamo97.sudokusolver.model.PuzzleDifficulty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SudokuRepository {

    suspend fun getPuzzles(): List<Puzzle> {

        return withContext(Dispatchers.Default) {
            val board = Board.createMockBoard()

            return@withContext List(100) { i -> Puzzle(i, i,  board, listOf(), PuzzleDifficulty.EASY, false) }
        }
    }
}
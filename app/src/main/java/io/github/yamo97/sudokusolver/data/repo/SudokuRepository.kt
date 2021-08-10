package io.github.yamo97.sudokusolver.data.repo

import io.github.yamo97.sudokusolver.model.Board
import io.github.yamo97.sudokusolver.model.Puzzle
import io.github.yamo97.sudokusolver.model.PuzzleDifficulty

class SudokuRepository {

    fun getPuzzles(): List<Puzzle> {

        val board = Board.createMockBoard()

        return List(100) { i -> Puzzle(i, i,  board, listOf(), PuzzleDifficulty.EASY, false) }
    }
}
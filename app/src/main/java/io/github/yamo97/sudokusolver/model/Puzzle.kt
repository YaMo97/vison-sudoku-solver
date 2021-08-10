package io.github.yamo97.sudokusolver.model

data class Puzzle(
    var puzzleID: Int,
    var puzzleNumber: Int,
    var board: Board,
    var solutionIDs: List<Int>,
    var puzzleDifficulty: PuzzleDifficulty,
    var isSolved: Boolean
)
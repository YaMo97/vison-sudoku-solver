package io.github.yamo97.sudokusolver.model

data class Cell (
    val row: Int,
    val col: Int,
    var value: Int,
    var isStartingCell: Boolean = false
)
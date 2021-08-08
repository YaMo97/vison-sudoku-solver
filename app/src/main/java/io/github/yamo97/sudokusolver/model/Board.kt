package io.github.yamo97.sudokusolver.model

data class Board(
    val size: Int,
    val cells: List<Cell>
) {
    fun getCell(row: Int, col: Int) = cells[row * size + col]

    companion object {
        fun createStartingBoard(): Board {
            val cells = List(9 * 9) { i -> Cell(i / 9, i % 9, i % 9) }

            cells[11].isStartingCell = true
            cells[21].isStartingCell = true

            return Board(9, cells)
        }
    }
}
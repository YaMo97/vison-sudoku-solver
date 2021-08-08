package io.github.yamo97.sudokusolver.model

import androidx.lifecycle.MutableLiveData

class SudokuGame {

    val selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    val cellsLiveData = MutableLiveData<List<Cell>>()

    private var selectedRow = -1
    private var selectedCol = -1

    private val board = Board.createStartingBoard()

    init {
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }

    fun handleInput(number: Int) {
        if (selectedRow == -1 || selectedCol == -1) return

        if ( board.getCell(selectedRow, selectedCol).isStartingCell ) return

        board.getCell(selectedRow, selectedCol).value = number
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, col: Int) {
        if ( board.getCell(row, col).isStartingCell ) return

        // Only update if selected Cell is not starting cell
        selectedRow = row
        selectedCol = col
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
    }

}
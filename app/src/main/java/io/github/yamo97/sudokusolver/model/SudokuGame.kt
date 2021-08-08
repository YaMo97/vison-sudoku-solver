package io.github.yamo97.sudokusolver.model

import androidx.lifecycle.MutableLiveData

class SudokuGame {

    val selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    val cellsLiveData = MutableLiveData<List<Cell>>()
    val isTakingNotesLiveData = MutableLiveData<Boolean>()
    val highlightedKeysLiveData = MutableLiveData<Set<Int>>()

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNotes = false

    private val board = Board.createStartingBoard()

    init {
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
        isTakingNotesLiveData.postValue(isTakingNotes)
    }

    fun handleInput(number: Int) {
        if (selectedRow == -1 || selectedCol == -1) return

        val cell = board.getCell(selectedRow, selectedCol)

        if ( cell.isStartingCell ) return

        if ( isTakingNotes ) {

            if ( cell.notes.contains(number) ) {
                cell.notes.remove(number)
            } else {
                cell.notes.add(number)
            }

            highlightedKeysLiveData.postValue(cell.notes)
        } else {
            cell.value = number
        }

        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, col: Int) {
        val cell = board.getCell(row, col)
        if ( cell.isStartingCell ) return

        // Only update if selected Cell is not starting cell
        selectedRow = row
        selectedCol = col
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))

        // Update highlightedKeysLiveData on selecting new cell.
        if (isTakingNotes)
            highlightedKeysLiveData.postValue(cell.notes)
    }


    fun changeNoteTakingState() {
        isTakingNotes = !isTakingNotes
        isTakingNotesLiveData.postValue(isTakingNotes)

        val curNotes = if (isTakingNotes) {
            board.getCell(selectedRow, selectedCol).notes
        } else {
            setOf()
        }

        highlightedKeysLiveData.postValue(curNotes)
    }
}
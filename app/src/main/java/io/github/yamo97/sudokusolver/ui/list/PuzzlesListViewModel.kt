package io.github.yamo97.sudokusolver.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.yamo97.sudokusolver.di.sudokuRepository
import io.github.yamo97.sudokusolver.model.Puzzle

class PuzzlesListViewModel() : ViewModel() {

    val puzzlesLiveData = MutableLiveData<List<Puzzle>>()

    fun fetchPuzzles() {
        puzzlesLiveData.postValue(sudokuRepository.getPuzzles())
    }
}
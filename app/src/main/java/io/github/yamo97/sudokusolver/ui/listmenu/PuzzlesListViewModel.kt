package io.github.yamo97.sudokusolver.ui.listmenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.yamo97.sudokusolver.di.sudokuRepository
import io.github.yamo97.sudokusolver.model.Puzzle
import kotlinx.coroutines.launch

class PuzzlesListViewModel : ViewModel() {

    val puzzlesLiveData = MutableLiveData<List<Puzzle>>()

    fun fetchPuzzles() {
        viewModelScope.launch {
            puzzlesLiveData.value = sudokuRepository.getPuzzles()
        }
    }
}
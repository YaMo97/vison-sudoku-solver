package io.github.yamo97.sudokusolver.ui.game

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import io.github.yamo97.sudokusolver.R
import io.github.yamo97.sudokusolver.databinding.ActivityGameBinding
import io.github.yamo97.sudokusolver.model.Cell
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.custom.SudokuBoardView

class GameActivity : BaseActivity<ActivityGameBinding>() {

    private lateinit var viewModel: GameViewModel

    private lateinit var numberButtons: List<Button>

    override fun getViewBinding(inflater: LayoutInflater): ActivityGameBinding
        = ActivityGameBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.sudokuGame.selectedCellLiveData.observe(this, { updateSelectedCellUI(it) })

        viewModel.sudokuGame.cellsLiveData.observe(this, { updateCells(it) })

        viewModel.sudokuGame.isTakingNotesLiveData.observe(this, { updateNotesTakingUI(it) })

        viewModel.sudokuGame.highlightedKeysLiveData.observe(this, { updateHighlightedKeys(it) })

        numberButtons = listOf(
            binding.buttonsLayout.oneButton,
            binding.buttonsLayout.twoButton,
            binding.buttonsLayout.threeButton,
            binding.buttonsLayout.fourButton,
            binding.buttonsLayout.fiveButton,
            binding.buttonsLayout.sixButton,
            binding.buttonsLayout.sevenButton,
            binding.buttonsLayout.eightButton,
            binding.buttonsLayout.nineButton
        )
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { viewModel.sudokuGame.handleInput(index + 1) }
        }

        binding.buttonsLayout.notesButton.setOnClickListener { viewModel.sudokuGame.changeNoteTakingState() }
        binding.buttonsLayout.deleteButton.setOnClickListener { viewModel.sudokuGame.deleteCell() }

        binding.sudokuBoardView.registerListener(object : SudokuBoardView.OnTouchListener {
            override fun onCellTouched(row: Int, col: Int) {
                viewModel.sudokuGame.updateSelectedCell(row, col)
            }
        })
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        binding.sudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    private fun updateNotesTakingUI(isTakingNotes: Boolean?) = isTakingNotes?.let {

        val color = if (it)
            ContextCompat.getColor(this, R.color.purple_500)
        else
            Color.LTGRAY

        binding.buttonsLayout.notesButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    private fun updateHighlightedKeys(notes: Set<Int>?)  = notes?.let {

        numberButtons.forEachIndexed { index, button ->
            val color = if ( notes.contains(index + 1) )
                ContextCompat.getColor(this, R.color.purple_500)
            else
                Color.LTGRAY

            button.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

}
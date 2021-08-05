package io.github.yamo97.sudokusolver.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import io.github.yamo97.sudokusolver.databinding.ActivityGameBinding
import io.github.yamo97.sudokusolver.model.Cell
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.custom.SudokuBoardView

class GameActivity : BaseActivity<ActivityGameBinding>() {

    private lateinit var viewModel: GameViewModel

    private var buttons: List<Button>? = null

    override fun getViewBinding(inflater: LayoutInflater): ActivityGameBinding
        = ActivityGameBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.sudokuGame.selectedCellLiveData.observe(this, { updateSelectedCellUI(it) })

        viewModel.sudokuGame.cellsLiveData.observe(this, { updateCells(it) })

        buttons = listOf(
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
        buttons?.forEachIndexed { index, button ->
            button.setOnClickListener { viewModel.sudokuGame.handleInput(index + 1) }
        }

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
}
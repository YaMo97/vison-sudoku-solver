package io.github.yamo97.sudokusolver.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import io.github.yamo97.sudokusolver.databinding.ActivityGameBinding
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.custom.SudokuBoardView

class GameActivity : BaseActivity<ActivityGameBinding>() {

    private lateinit var viewModel: GameViewModel

    override fun getViewBinding(inflater: LayoutInflater): ActivityGameBinding
        = ActivityGameBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this, { updateSelectedCellUI(it) })

        binding.sudokuBoardView.registerListener(object : SudokuBoardView.OnTouchListener {
            override fun onCellTouched(row: Int, col: Int) {
                viewModel.sudokuGame.updateSelectedCell(row, col)
            }
        })
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }
}
package io.github.yamo97.sudokusolver.ui.game

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import io.github.yamo97.sudokusolver.R
import io.github.yamo97.sudokusolver.databinding.ActivityGameBinding
import io.github.yamo97.sudokusolver.model.Cell
import io.github.yamo97.sudokusolver.model.GameState
import io.github.yamo97.sudokusolver.model.SudokuGame
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.custom.SudokuBoardView
import io.github.yamo97.sudokusolver.util.toast

class GameActivity : BaseActivity<ActivityGameBinding>() {

    private val TAG = GameActivity::class.simpleName

    private lateinit var viewModel: GameViewModel

    private lateinit var numberButtons: List<Button>

    override fun getViewBinding(inflater: LayoutInflater): ActivityGameBinding
        = ActivityGameBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.gameStatusLiveData.observe(this) { handleGameState(it) }

        viewModel.puzzleID = intent?.getIntExtra(INTENT_PUZZLE_ID, -1) ?: -1

        viewModel.loadAndStartGame()
    }

    private fun handleGameState(gameState: GameState?) = gameState?.let {
        Log.d(TAG, "Game State -> ${gameState.name}")
        when (gameState) {
            GameState.LOADING -> {
                // Show Empty Board and Progress Bar
                binding.loadingBar.visibility = View.VISIBLE
            }
            GameState.PLAYING -> {
                // Start Playing

                setupGameObserversAndButtons(viewModel.sudokuGame)

                // Remove Loading Signs
                binding.loadingBar.visibility = View.GONE
            }
            GameState.COMPLETED -> {

            }
            GameState.ERROR -> {
                toast("Couldn't start the Game. Please try again.")
            }
        }
    }

    private fun setupGameObserversAndButtons(sudokuGame: SudokuGame?) = sudokuGame?.let {
        sudokuGame.selectedCellLiveData.observe(this@GameActivity, { updateSelectedCellUI(it) })
        sudokuGame.cellsLiveData.observe(this@GameActivity, { updateCells(it) })
        sudokuGame.isTakingNotesLiveData.observe(this@GameActivity, { updateNotesTakingUI(it) })
        sudokuGame.highlightedKeysLiveData.observe(this@GameActivity, { updateHighlightedKeys(it) })

        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { sudokuGame.handleInput(index + 1) }
        }

        binding.buttonsLayout.notesButton.setOnClickListener { sudokuGame.changeNoteTakingState() }
        binding.buttonsLayout.deleteButton.setOnClickListener { sudokuGame.deleteCell() }

        binding.sudokuBoardView.registerListener(object : SudokuBoardView.OnTouchListener {
            override fun onCellTouched(row: Int, col: Int) {
                sudokuGame.updateSelectedCell(row, col)
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

    companion object {
        const val INTENT_PUZZLE_ID = "puzzle-id"
    }
}
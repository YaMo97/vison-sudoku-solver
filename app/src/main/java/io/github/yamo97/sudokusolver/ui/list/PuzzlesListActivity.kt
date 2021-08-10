package io.github.yamo97.sudokusolver.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.github.yamo97.sudokusolver.databinding.ActivityPuzzlesListBinding
import io.github.yamo97.sudokusolver.model.Puzzle
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.game.GameActivity
import io.github.yamo97.sudokusolver.util.toast

class PuzzlesListActivity : BaseActivity<ActivityPuzzlesListBinding>() {

    private lateinit var mViewModel: PuzzlesListViewModel

    private lateinit var puzzlesListAdapter: PuzzlesListAdapter

    override fun getViewBinding(inflater: LayoutInflater): ActivityPuzzlesListBinding =
        ActivityPuzzlesListBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show Progress Bar & Hide Recycler View
        binding.progressBar.visibility = View.VISIBLE
        binding.puzzlesListRecyclerView.visibility = View.GONE

        mViewModel = ViewModelProvider(this).get(PuzzlesListViewModel::class.java)

        mViewModel.puzzlesLiveData.observe(this) { updatePuzzlesRecyclerView(it) }

        puzzlesListAdapter = PuzzlesListAdapter(listOf()) { puzzle: Puzzle ->
            onPuzzleClicked(puzzle)
        }

        binding.puzzlesListRecyclerView.adapter = puzzlesListAdapter

        binding.puzzlesListRecyclerView.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        mViewModel.fetchPuzzles()
    }

    private fun updatePuzzlesRecyclerView(puzzlesList: List<Puzzle>?) = puzzlesList?.let {
        puzzlesListAdapter.updateList(it)

        // Hide Progress Bar & Show Recycler View
        binding.puzzlesListRecyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun onPuzzleClicked(puzzle: Puzzle) {
        toast("Puzzle selected ${ puzzle.puzzleNumber }")

        startActivity(Intent(this, GameActivity::class.java))
    }
}
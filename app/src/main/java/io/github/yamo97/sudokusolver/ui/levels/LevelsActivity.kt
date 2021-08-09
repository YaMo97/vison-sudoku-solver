package io.github.yamo97.sudokusolver.ui.levels

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.github.yamo97.sudokusolver.databinding.ActivityLevelsBinding
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.game.GameActivity
import io.github.yamo97.sudokusolver.util.toast

class LevelsActivity : BaseActivity<ActivityLevelsBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): ActivityLevelsBinding =
        ActivityLevelsBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val levelsList = (1..100).toList()

        binding.levelsRecyclerView.adapter = LevelsListAdapter(levelsList) { levelNumber: Int ->
            onLevelClicked(levelNumber)
        }

        binding.levelsRecyclerView.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
 
    }

    private fun onLevelClicked(levelNumber: Int) {
        toast("Level selected $levelNumber")

        startActivity(Intent(this, GameActivity::class.java))
    }
}
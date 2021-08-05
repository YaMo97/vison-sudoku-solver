package io.github.yamo97.sudokusolver.ui.game

import android.view.LayoutInflater
import io.github.yamo97.sudokusolver.databinding.ActivityGameBinding
import io.github.yamo97.sudokusolver.ui.BaseActivity

class GameActivity : BaseActivity<ActivityGameBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): ActivityGameBinding
        = ActivityGameBinding.inflate(inflater)
}
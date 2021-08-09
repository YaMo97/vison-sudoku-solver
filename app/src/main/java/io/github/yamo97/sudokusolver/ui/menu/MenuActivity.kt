package io.github.yamo97.sudokusolver.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import io.github.yamo97.sudokusolver.databinding.ActivityMenuBinding
import io.github.yamo97.sudokusolver.ui.BaseActivity
import io.github.yamo97.sudokusolver.ui.game.GameActivity
import io.github.yamo97.sudokusolver.ui.levels.LevelsActivity
import io.github.yamo97.sudokusolver.util.toast

class MenuActivity : BaseActivity<ActivityMenuBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): ActivityMenuBinding {
        return ActivityMenuBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.scanButton.setOnClickListener {
            toast("To be implemented!")
        }

        binding.solveButton.setOnClickListener {
            startActivity(Intent(this, LevelsActivity::class.java))
        }
    }
}
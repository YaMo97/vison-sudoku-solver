package io.github.yamo97.sudokusolver.ui.levels

import android.os.Bundle
import android.view.LayoutInflater
import io.github.yamo97.sudokusolver.databinding.ActivityLevelsBinding
import io.github.yamo97.sudokusolver.ui.BaseActivity

class LevelsActivity : BaseActivity<ActivityLevelsBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): ActivityLevelsBinding =
        ActivityLevelsBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
package io.github.yamo97.sudokusolver.ui.levels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import io.github.yamo97.sudokusolver.R

class LevelsListAdapter(private val list: List<Int>,
                        private val onLevelClicked: (Int) -> Unit) :
    RecyclerView.Adapter<LevelsListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_levels_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.button.text = item.toString()
        holder.button.setOnClickListener { onLevelClicked(item) }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button =  itemView.findViewById(R.id.levelButton)
    }
}
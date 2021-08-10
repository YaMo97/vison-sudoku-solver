package io.github.yamo97.sudokusolver.ui.listmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import io.github.yamo97.sudokusolver.R
import io.github.yamo97.sudokusolver.model.Puzzle

class PuzzlesListAdapter(private var list: List<Puzzle>,
                         private val onPuzzleClicked: (Puzzle) -> Unit) :
    RecyclerView.Adapter<PuzzlesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_puzzles_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val level = list[position]

        holder.button.text = level.puzzleNumber.toString()
        holder.button.setOnClickListener { onPuzzleClicked(level) }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Puzzle>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button =  itemView.findViewById(R.id.puzzleButton)
    }
}
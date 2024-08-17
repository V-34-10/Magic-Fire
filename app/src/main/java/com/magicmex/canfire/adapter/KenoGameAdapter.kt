package com.magicmex.canfire.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.magicmex.canfire.R
import com.magicmex.canfire.model.KenoGame

class KenoGameAdapter(
    private val kenoNumbers: List<KenoGame>,
    private val selectedNumbers: MutableList<KenoGame>,
    private val winningNumbers: List<KenoGame>,
    private val onItemClickListener: (KenoGame) -> Unit
) : RecyclerView.Adapter<KenoGameAdapter.KenoGameViewHolder>() {

    class KenoGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNumber: TextView = itemView.findViewById(R.id.text_number)
        val defaultOverlay: ConstraintLayout = itemView.findViewById(R.id.default_overlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KenoGameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.keno_item, parent, false)
        return KenoGameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KenoGameViewHolder, position: Int) {
        val number = kenoNumbers[position]

        holder.textNumber.text = number.index.toString()

        holder.defaultOverlay.setBackgroundResource(R.drawable.keno_background_default)

        if (selectedNumbers.contains(number)) {
            holder.defaultOverlay.setBackgroundResource(R.drawable.keno_selected_background_default)
        }

        if (winningNumbers.contains(number)) {
            holder.defaultOverlay.setBackgroundResource(R.drawable.keno_selected_win_background)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener(number)
        }
    }

    override fun getItemCount() = kenoNumbers.size
}
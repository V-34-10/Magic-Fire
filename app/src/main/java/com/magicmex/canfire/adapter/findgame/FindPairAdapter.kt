package com.magicmex.canfire.adapter.findgame

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magicmex.canfire.R
import com.magicmex.canfire.model.findgame.FindPair

class FindPairAdapter(
    private val pairFindList: List<FindPair>,
    private val levelGame: String
) :
    RecyclerView.Adapter<FindPairViewHolder>() {

    var onFindPairClick: ((FindPair, Int) -> Unit)? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindPairViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.find_pair_item, parent, false)

        val cardSize = calculateCardSize(parent.context, levelGame)
        adjustItemViewSize(itemView, cardSize)

        return FindPairViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FindPairViewHolder, position: Int) {
        val pairItem = pairFindList[position]
        holder.pairImage.setImageResource(
            if (pairItem.flip) pairItem.img else R.drawable.find_0
        )

        holder.itemView.setOnClickListener {
            onFindPairClick?.invoke(pairItem, position)
        }
    }

    override fun getItemCount(): Int = pairFindList.size

    private fun calculateCardSize(context: Context, levelGame: String): Int {
        return when (levelGame) {
            "Level 2" -> 60
            "Level 3" -> 50
            else -> 70
        }.dpConvertToPx(context)
    }

    private fun adjustItemViewSize(itemView: View, size: Int) {
        itemView.findViewById<ImageView>(R.id.find_card_item).apply {
            layoutParams = layoutParams.apply {
                width = size
                height = size
            }
        }
    }

    private fun Int.dpConvertToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()
}
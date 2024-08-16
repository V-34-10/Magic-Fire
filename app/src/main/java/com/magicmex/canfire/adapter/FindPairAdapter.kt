package com.magicmex.canfire.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magicmex.canfire.R
import com.magicmex.canfire.model.FindPair

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

        val cardSize = when (levelGame) {
            "Level 2" -> 60.dpConvertToPx(parent.context)
            "Level 3" -> 50.dpConvertToPx(parent.context)
            else -> 70.dpConvertToPx(parent.context)
        }

        itemView.findViewById<ImageView>(R.id.find_card_item).apply {
            layoutParams = layoutParams.apply {
                width = cardSize
                height = cardSize
            }
        }

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

    private fun Int.dpConvertToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()
}
package com.magicmex.canfire.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magicmex.canfire.R

class FindPairViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val pairImage: ImageView = itemView.findViewById(R.id.find_card_item)
}
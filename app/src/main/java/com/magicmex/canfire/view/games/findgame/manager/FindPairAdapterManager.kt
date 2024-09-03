package com.magicmex.canfire.view.games.findgame.manager

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.magicmex.canfire.adapter.findgame.FindPairAdapter
import com.magicmex.canfire.model.findgame.FindPair
import com.magicmex.canfire.utils.preference.PreferenceManager.selectedLevel

class FindPairAdapterManager(
    private val binding: ViewBinding,
    private val context: Context,
    private val uiManager: UIManager
) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FindPairAdapter

    fun initRecyclerView(context: Context, selectedLevel: String) {
        recyclerView = uiManager.setupRecyclerView(context, selectedLevel)
    }

    fun setupAdapter(pairList: List<FindPair>) {
        adapter = FindPairAdapter(pairList, selectedLevel)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetAdapter() {
        adapter.notifyDataSetChanged()
    }

    fun notifyItemChanged(position: Int) {
        adapter.notifyItemChanged(position)
    }

    fun setOnItemClickListener(onClick: (FindPair, Int) -> Unit) {
        adapter.onFindPairClick = onClick
    }
}
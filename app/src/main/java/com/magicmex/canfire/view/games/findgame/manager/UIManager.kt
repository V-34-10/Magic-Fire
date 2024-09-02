package com.magicmex.canfire.view.games.findgame.manager

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.FragmentFindPairGameBinding

class UIManager(private val binding: ViewBinding) {

    fun setupRecyclerView(
        context: Context,
        selectedLevelPairGame: String
    ): RecyclerView {
        val recyclerView = when (binding) {
            is FragmentFindPairGameBinding -> binding.sceneGame
            else -> throw IllegalArgumentException("Unsupported binding type")
        }
        recyclerView.layoutManager =
            GridLayoutManager(context, selectedLevelPairGame.getSpanCount())
        return recyclerView
    }

    fun updateTextStepPair(stepCount: Int) {
        if (binding is FragmentFindPairGameBinding) binding.textSteps.text = stepCount.toString()
    }

    fun showWinMessage(context: Context) =
        Toast.makeText(context, R.string.toast_win, Toast.LENGTH_SHORT).show()

    fun resetUI() = updateTextStepPair(0)
}

fun String.getSpanCount(): Int = when (this) {
    "Level 1" -> 3
    "Level 2" -> 4
    "Level 3" -> 5
    else -> 3
}
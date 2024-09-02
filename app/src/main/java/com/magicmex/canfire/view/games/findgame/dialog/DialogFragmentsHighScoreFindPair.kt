package com.magicmex.canfire.view.games.findgame.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.magicmex.canfire.R
import com.magicmex.canfire.view.games.findgame.dialog.HighScoreFindPairManager.loadScoreFindPairGame

object DialogFragmentsHighScoreFindPair {

    private fun initScoreFindPairGame(preferences: SharedPreferences, dialogView: View) {
        loadScoreFindPairGame(preferences)
        val stats = HighScoreFindPairManager.statsHighScoreFindPair

        val viewHolder = createViewHolder(dialogView)

        viewHolder.levels.forEach { (level, views) ->
            val levelStats = stats[level]!!
            setScoreText(views.first, levelStats.bestTime)
            views.second.text = levelStats.bestSteps.toString()
        }
    }

    private fun createViewHolder(dialogView: View): ViewHolder {
        return ViewHolder(
            mapOf(
                "Level 1" to Pair(
                    dialogView.findViewById(R.id.time_level_first),
                    dialogView.findViewById(R.id.steps_level_first)
                ),
                "Level 2" to Pair(
                    dialogView.findViewById(R.id.time_level_second),
                    dialogView.findViewById(R.id.steps_level_second)
                ),
                "Level 3" to Pair(
                    dialogView.findViewById(R.id.time_level_three),
                    dialogView.findViewById(R.id.steps_level_three)
                )
            )
        )
    }

    private data class ViewHolder(
        val levels: Map<String, Pair<TextView, TextView>>
    )

    @SuppressLint("SetTextI18n")
    private fun setScoreText(textView: TextView, bestTime: Long) {
        val formattedTime = if (bestTime == Long.MAX_VALUE) "00:00" else formatTime(bestTime)
        textView.text = formattedTime
    }

    private fun formatTime(timeInMillis: Long): String =
        String.format("%02d:%02d", (timeInMillis / 1000) / 60, (timeInMillis / 1000) % 60)

    fun runDialogFindPairGame(context: Context, preferences: SharedPreferences) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fragment_find_pair_high_score)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)

        val dialogView = dialog.findViewById<ConstraintLayout>(R.id.scoreFindPair)
        initScoreFindPairGame(preferences, dialogView)

        dialog.show()
    }
}
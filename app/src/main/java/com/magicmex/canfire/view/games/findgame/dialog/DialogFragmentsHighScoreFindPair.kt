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

        val easyStats = stats["Level 1"]!!
        val mediumStats = stats["Level 2"]!!
        val hardStats = stats["Level 3"]!!

        val timeLevelFirst = dialogView.findViewById<TextView>(R.id.time_level_first)
        val stepsLevelFirst = dialogView.findViewById<TextView>(R.id.steps_level_first)
        val timeLevelSecond = dialogView.findViewById<TextView>(R.id.time_level_second)
        val stepsLevelSecond = dialogView.findViewById<TextView>(R.id.steps_level_second)
        val timeLevelThree = dialogView.findViewById<TextView>(R.id.time_level_three)
        val stepsLevelThree = dialogView.findViewById<TextView>(R.id.steps_level_three)

        setScoreText(timeLevelFirst, easyStats.bestTime)
        stepsLevelFirst.text = easyStats.bestSteps.toString()

        setScoreText(timeLevelSecond, mediumStats.bestTime)
        stepsLevelSecond.text = mediumStats.bestSteps.toString()

        setScoreText(timeLevelThree, hardStats.bestTime)
        stepsLevelThree.text = hardStats.bestSteps.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setScoreText(textView: TextView, bestTime: Long) {
        val formattedTime = if (bestTime == Long.MAX_VALUE) {
            "00:00"
        } else {
            formatTime(bestTime)
        }
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
package com.magicmex.canfiree.view.games.kenogame.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.magicmex.canfiree.R
import com.magicmex.canfiree.model.kenogame.HighScoreKeno
import com.magicmex.canfiree.view.games.kenogame.dialog.HighScoreKenoManager.loadScoreKenoGame

object DialogFragmentsHighScoreKeno {

    private data class LevelScoreData(
        val winRateTextView: TextView,
        val winsTextView: TextView,
        val losesTextView: TextView
    )

    @SuppressLint("SetTextI18n")
    private fun setupLevelScore(
        dialogView: View,
        level: String,
        stats: HighScoreKeno
    ) {
        val levelScoreData = when (level) {
            "Level 1" -> LevelScoreData(
                dialogView.findViewById(R.id.win_rate_level_first),
                dialogView.findViewById(R.id.wins_level_first),
                dialogView.findViewById(R.id.loses_level_first)
            )
            "Level 2" -> LevelScoreData(
                dialogView.findViewById(R.id.win_rate_level_second),
                dialogView.findViewById(R.id.wins_level_second),
                dialogView.findViewById(R.id.loses_level_second)
            )
            "Level 3" -> LevelScoreData(
                dialogView.findViewById(R.id.win_rate_level_three),
                dialogView.findViewById(R.id.wins_level_three),
                dialogView.findViewById(R.id.loses_level_three)
            )
            else -> return
        }

        levelScoreData.winRateTextView.text =
            "%02d%%".format(calculateWinRateKeno(stats).toInt())
        levelScoreData.winsTextView.text = stats.countWins.toString()
        levelScoreData.losesTextView.text = stats.countLosses.toString()
    }

    private fun calculateWinRateKeno(levelStats: HighScoreKeno): Float {
        return if (levelStats.countAllGamesPlayed > 0) {
            (levelStats.countWins.toFloat() / levelStats.countAllGamesPlayed) * 100
        } else {
            0f
        }
    }

    fun runDialogKenoGame(context: Context, preferences: SharedPreferences) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fragment_keno_high_score)
        dialog.setCanceledOnTouchOutside(false)

        val dialogView = dialog.findViewById<ConstraintLayout>(R.id.scoreKeno)

        loadScoreKenoGame(preferences)
        HighScoreKenoManager.statsHighScoreKeno.forEach { (level, stats) ->
            setupLevelScore(dialogView, level, stats)
        }

        dialog.show()
    }
}
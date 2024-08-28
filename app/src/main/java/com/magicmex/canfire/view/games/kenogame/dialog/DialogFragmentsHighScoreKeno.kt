package com.magicmex.canfire.view.games.kenogame.dialog

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
import com.magicmex.canfire.model.kenogame.HighScoreKeno
import com.magicmex.canfire.view.games.kenogame.dialog.HighScoreKenoManager.loadScoreKenoGame

object DialogFragmentsHighScoreKeno {

    @SuppressLint("SetTextI18n")
    private fun setHighScoreKeno(preferences: SharedPreferences, dialogView: View) {
        loadScoreKenoGame(preferences)

        val easyStatsEasy = HighScoreKenoManager.statsHighScoreKeno["Level 1"]!!
        val easyStatsMedium = HighScoreKenoManager.statsHighScoreKeno["Level 2"]!!
        val easyStatsHard = HighScoreKenoManager.statsHighScoreKeno["Level 3"]!!

        val winRateLevelFirst = dialogView.findViewById<TextView>(R.id.win_rate_level_first)
        val winsLevelFirst = dialogView.findViewById<TextView>(R.id.wins_level_first)
        val losesLevelFirst = dialogView.findViewById<TextView>(R.id.loses_level_first)

        val winRateLevelSecond = dialogView.findViewById<TextView>(R.id.win_rate_level_second)
        val winsLevelSecond = dialogView.findViewById<TextView>(R.id.wins_level_second)
        val losesLevelSecond = dialogView.findViewById<TextView>(R.id.loses_level_second)

        val winRateLevelThree = dialogView.findViewById<TextView>(R.id.win_rate_level_three)
        val winsLevelThree = dialogView.findViewById<TextView>(R.id.wins_level_three)
        val losesLevelThree = dialogView.findViewById<TextView>(R.id.loses_level_three)

        winRateLevelFirst.text = "%02d%%".format(calculateWinRateKeno(easyStatsEasy).toInt())
        winsLevelFirst.text = easyStatsEasy.countWins.toString()
        losesLevelFirst.text = easyStatsEasy.countLosses.toString()

        winRateLevelSecond.text = " %02d%%".format(calculateWinRateKeno(easyStatsMedium).toInt())
        winsLevelSecond.text = easyStatsMedium.countWins.toString()
        losesLevelSecond.text = easyStatsMedium.countLosses.toString()

        winRateLevelThree.text = "%02d%%".format(calculateWinRateKeno(easyStatsHard).toInt())
        winsLevelThree.text = easyStatsHard.countWins.toString()
        losesLevelThree.text = easyStatsHard.countLosses.toString()
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
        setHighScoreKeno(preferences, dialogView)

        dialog.show()
    }
}
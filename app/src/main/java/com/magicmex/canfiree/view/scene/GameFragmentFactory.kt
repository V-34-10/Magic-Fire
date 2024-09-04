package com.magicmex.canfiree.view.scene

import android.content.Context
import androidx.fragment.app.Fragment
import com.magicmex.canfiree.R
import com.magicmex.canfiree.view.games.findgame.FindPairGameFragment
import com.magicmex.canfiree.view.games.kenogame.KenoGameFragment

class GameFragmentFactory (private val context: Context) {

    fun createFragment(game: String?): Fragment {
        return when (game) {
            context.getString(R.string.button_game_second) -> FindPairGameFragment()
            else -> KenoGameFragment()
        }
    }
}
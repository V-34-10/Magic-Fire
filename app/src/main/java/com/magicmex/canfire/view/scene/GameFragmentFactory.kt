package com.magicmex.canfire.view.scene

import android.content.Context
import androidx.fragment.app.Fragment
import com.magicmex.canfire.R
import com.magicmex.canfire.view.games.findgame.FindPairGameFragment
import com.magicmex.canfire.view.games.kenogame.KenoGameFragment

class GameFragmentFactory (private val context: Context) {

    fun createFragment(game: String?): Fragment {
        return when (game) {
            context.getString(R.string.button_game_second) -> FindPairGameFragment()
            else -> KenoGameFragment()
        }
    }
}
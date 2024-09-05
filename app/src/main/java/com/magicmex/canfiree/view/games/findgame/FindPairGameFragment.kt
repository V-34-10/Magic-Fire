package com.magicmex.canfiree.view.games.findgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.magicmex.canfiree.R
import com.magicmex.canfiree.databinding.FragmentFindPairGameBinding
import com.magicmex.canfiree.utils.preference.PreferenceManager
import com.magicmex.canfiree.view.games.BaseGameFragment
import com.magicmex.canfiree.view.games.findgame.dialog.DialogFragmentsHighScoreFindPair
import com.magicmex.canfiree.view.games.findgame.manager.FindPairGameManager
import com.magicmex.canfiree.view.settings.music.MusicStart

class FindPairGameFragment : BaseGameFragment() {
    private lateinit var binding: FragmentFindPairGameBinding
    private lateinit var findPairGameManager: FindPairGameManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindPairGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { findPairGameManager = FindPairGameManager(it, binding) }
        findPairGameManager.initGame(PreferenceManager.selectedLevel)
    }

    override fun startMusic() {
        MusicStart.musicStartMode(R.raw.music_find_pair, controllerMusic)
    }

    override fun setUIBackgroundDependedLevel() {
        when (PreferenceManager.selectedLevel) {
            "Level 1" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_first)
            "Level 2" -> {
                binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_second)
                binding.backgroundGameFindPair.setBackgroundResource(R.drawable.background_find_pair_game_level_second)
            }
            "Level 3" -> {
                binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_three)
                binding.backgroundGameFindPair.setBackgroundResource(R.drawable.background_find_pair_game_level_three)
            }
        }
    }

    override fun handleClaimButtonClick() {
        findPairGameManager.resetGame()
    }

    override fun handleHighScoreButtonClick() {
        findPairGameManager.resetGame()
        DialogFragmentsHighScoreFindPair.runDialogFindPairGame(
            requireContext(),
            PreferenceManager.getPreference(requireContext())
        )
    }
}
package com.magicmex.canfiree.view.games.kenogame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.magicmex.canfiree.R
import com.magicmex.canfiree.databinding.FragmentKenoGameBinding
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.preference.PreferenceManager
import com.magicmex.canfiree.view.games.BaseGameFragment
import com.magicmex.canfiree.view.games.kenogame.dialog.DialogFragmentsHighScoreKeno
import com.magicmex.canfiree.view.games.kenogame.manager.ManagerKeno
import com.magicmex.canfiree.view.games.kenogame.manager.ManagerKeno.initRecyclerKenoScene
import com.magicmex.canfiree.view.settings.music.MusicStart


class KenoGameFragment : BaseGameFragment() {
    private lateinit var binding: FragmentKenoGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKenoGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { initRecyclerKenoScene(binding, it) }
        ManagerKeno.startTimer(binding)
        binding.btnRestart.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            ManagerKeno.restartGame(requireContext(), binding)
        }
    }

    override fun startMusic() {
        MusicStart.musicStartMode(R.raw.music_keno, controllerMusic)
    }

    override fun setUIBackgroundDependedLevel() {
        when (PreferenceManager.selectedLevel) {
            "Level 1" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_first_keno)
            "Level 2" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_second_keno)
            "Level 3" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_three_keno)
        }
    }

    override fun handleClaimButtonClick() {
        ManagerKeno.startKenoGame(requireContext(), binding)
    }

    override fun handleHighScoreButtonClick() {
        DialogFragmentsHighScoreKeno.runDialogKenoGame(
            requireContext(),
            PreferenceManager.getPreference(requireContext())
        )
    }
}
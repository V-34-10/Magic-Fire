package com.magicmex.canfire.view.games.kenogame

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.FragmentKenoGameBinding
import com.magicmex.canfire.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.view.games.kenogame.dialog.DialogFragmentsHighScoreKeno
import com.magicmex.canfire.view.games.kenogame.manager.ManagerKeno
import com.magicmex.canfire.view.games.kenogame.manager.ManagerKeno.initRecyclerKenoScene
import com.magicmex.canfire.view.level.LevelsActivity
import com.magicmex.canfire.view.settings.music.MusicController
import com.magicmex.canfire.view.settings.music.MusicStart

class KenoGameFragment : Fragment() {
    private lateinit var binding: FragmentKenoGameBinding
    private lateinit var preferencesApp: SharedPreferences
    private lateinit var controllerMusic: MusicController
    private var levelKenoGame: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKenoGameBinding.inflate(layoutInflater, container, false)

        controllerMusic = context?.let { MusicController(it) }!!
        preferencesApp = PreferenceManager.getPreference(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MusicStart.musicStartMode(R.raw.music_keno, controllerMusic, preferencesApp)
        PreferenceManager.init(requireContext())
        setUIBackgroundDependedLevel()
        context?.let { initRecyclerKenoScene(binding, it) }
        initControlBarKenoGame()
        ManagerKeno.startTimer(binding)
    }

    private fun setUIBackgroundDependedLevel() {
        when (PreferenceManager.selectedLevel) {
            "Level 1" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_first_keno)
            "Level 2" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_second_keno)
            "Level 3" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_three_keno)
        }
    }

    private fun initControlBarKenoGame() {
        binding.btnClaim.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            ManagerKeno.startKenoGame(requireContext(), binding)
        }
        binding.btnRestart.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            ManagerKeno.restartGame(requireContext(), binding)
        }
        binding.btnHighScore.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            DialogFragmentsHighScoreKeno.runDialogKenoGame(requireContext(), preferencesApp)
        }
        binding.btnChange.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            startActivity(Intent(context, LevelsActivity::class.java))
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        controllerMusic.resume()
    }

    override fun onPause() {
        super.onPause()
        controllerMusic.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        controllerMusic.release()
    }
}
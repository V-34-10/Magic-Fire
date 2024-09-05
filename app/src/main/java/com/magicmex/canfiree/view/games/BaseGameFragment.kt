package com.magicmex.canfiree.view.games

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magicmex.canfiree.R
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.preference.PreferenceManager
import com.magicmex.canfiree.utils.preference.PreferenceManager.initSettings
import com.magicmex.canfiree.view.level.LevelsActivity
import com.magicmex.canfiree.view.settings.music.MusicController

abstract class BaseGameFragment : Fragment() {
    protected lateinit var controllerMusic: MusicController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controllerMusic = MusicController(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettings(requireContext())
        startMusic()
        PreferenceManager.initLevelGame(requireContext())
        setUIBackgroundDependedLevel()
        initControlBarGame()
    }

    protected abstract fun startMusic()

    protected abstract fun setUIBackgroundDependedLevel()

    private fun initControlBarGame() {
        val btnClaim = requireView().findViewById<View>(R.id.btn_claim)
        val btnHighScore = requireView().findViewById<View>(R.id.btn_high_score)
        val btnChange = requireView().findViewById<View>(R.id.btn_change)

        btnClaim.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            handleClaimButtonClick()
        }

        btnHighScore.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            handleHighScoreButtonClick()
        }

        btnChange.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            startActivity(Intent(context, LevelsActivity::class.java))
            activity?.finish()
        }
    }

    protected abstract fun handleClaimButtonClick()

    protected abstract fun handleHighScoreButtonClick()

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
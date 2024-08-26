package com.magicmex.canfire.view.games.findgame

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.FragmentFindPairGameBinding
import com.magicmex.canfire.view.games.findgame.dialog.DialogFragmentsHighScoreFindPair
import com.magicmex.canfire.view.games.findgame.manager.GameSettings
import com.magicmex.canfire.view.games.findgame.manager.ManagerFindPair
import com.magicmex.canfire.view.level.LevelsActivity
import com.magicmex.canfire.view.settings.MusicController
import com.magicmex.canfire.view.settings.MusicStart

class FindPairGameFragment : Fragment() {
    private lateinit var binding: FragmentFindPairGameBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var controllerMusic: MusicController
    private var levelPairGame: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindPairGameBinding.inflate(layoutInflater, container, false)

        controllerMusic = context?.let { MusicController(it) }!!
        preferences =
            requireActivity().getSharedPreferences(
                "MagicMexicanFirePref",
                AppCompatActivity.MODE_PRIVATE
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MusicStart.musicStartMode(R.raw.music_find_pair, controllerMusic, preferences)

        levelPairGame = preferences.getString("LevelGame", "").toString()
        when (levelPairGame) {
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

        GameSettings.init(requireContext())
        context?.let { ManagerFindPair.initFindPairGame(binding, it) }
        initControlBarGame()
    }

    private fun initControlBarGame() {
        var animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
        binding.btnClaim.setOnClickListener {
            it.startAnimation(animationButton)
            ManagerFindPair.resetFindPairGame(binding)
        }
        binding.btnHighScore.setOnClickListener {
            animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            it.startAnimation(animationButton)
            ManagerFindPair.resetFindPairGame(binding)
            DialogFragmentsHighScoreFindPair.runDialogFindPairGame(requireContext())
        }
        binding.btnChange.setOnClickListener {
            animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            it.startAnimation(animationButton)
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
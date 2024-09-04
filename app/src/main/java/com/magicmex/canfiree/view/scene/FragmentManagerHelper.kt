package com.magicmex.canfiree.view.scene

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

class FragmentManagerHelper(private val activity: AppCompatActivity) {

    fun replaceFragment(fragment: Fragment, containerId: Int) {
        activity.supportFragmentManager.commit {
            replace(containerId, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    fun handleBackStack(): Boolean {
        return if (activity.supportFragmentManager.backStackEntryCount > 0) {
            activity.supportFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }
}
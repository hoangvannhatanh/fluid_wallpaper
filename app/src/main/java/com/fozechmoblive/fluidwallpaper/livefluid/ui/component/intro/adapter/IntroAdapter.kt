package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.intro.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.intro.IntroFragment

class IntroAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {

        val tp: Fragment = when (position) {
            0 -> {
                IntroFragment.newInstance(valueNumberIntroduce = 0)
            }

            1 -> {
                IntroFragment.newInstance(valueNumberIntroduce = 1)
            }

            2 -> {
                IntroFragment.newInstance(valueNumberIntroduce = 2)
            }

            else -> {
                IntroFragment.newInstance(valueNumberIntroduce = 0)
            }
        }
        return tp
    }

    override fun getCount(): Int {
        return 3
    }
}
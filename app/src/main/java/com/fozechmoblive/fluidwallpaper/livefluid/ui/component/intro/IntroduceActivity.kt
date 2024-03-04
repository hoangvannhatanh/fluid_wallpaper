package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.intro

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityIntroduceBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.getPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.onClick
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setPref
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.intro.adapter.IntroAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.HomeThemesActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.DepthPageTransformer
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes

class IntroduceActivity : BaseActivity<ActivityIntroduceBinding>() {
    private val mViewPagerAdapter by lazy { IntroAdapter(supportFragmentManager) }

    override fun getLayoutActivity(): Int = R.layout.activity_introduce

    override fun initViews() {
        bindComponent()
        bindEvent()
    }

    override fun onClickViews() {

    }

    private fun gotoNextScreen() {
        Routes.startMainActivity(this)
        finish()

    }

    private fun bindComponent() {
        initAdp()
    }

    private fun initAdp() {
        binding.profileViewpager.offscreenPageLimit = mViewPagerAdapter.count
        binding.profileViewpager.adapter = mViewPagerAdapter
        binding.profileViewpager.currentItem = 0
        binding.indicator.attachTo(binding.profileViewpager)
        binding.profileViewpager.setPageTransformer(true, DepthPageTransformer())
    }

    private fun bindEvent() {
        binding.btnNext.onClick(1000) {
            if (binding.profileViewpager.currentItem < mViewPagerAdapter.count - 1) {
                binding.profileViewpager.currentItem = binding.profileViewpager.currentItem + 1
            } else {
                if (getPref(
                        this@IntroduceActivity,
                        AppConstants.IS_FIRST_TIME_INTRO,
                        false
                    )
                ) {
                    startActivity(
                        Intent(
                            this@IntroduceActivity,
                            HomeThemesActivity::class.java
                        )
                    )
                    finish()
                } else {
                    startActivity(
                        Intent(
                            this@IntroduceActivity,
                            HomeThemesActivity::class.java
                        )
                    )
                    setPref(this@IntroduceActivity, AppConstants.IS_FIRST_TIME_INTRO, true)
                    finish()
                }
            }
        }

        binding.profileViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.tvTitle.text = getString(R.string.title_one_introduce)
                        binding.tvTitle.setTextColor(ContextCompat.getColor(this@IntroduceActivity, R.color.color_7BD9B1))
                        binding.tvDescription.text = getString(R.string.description_one_introduce)
                        binding.btnNext.text = getString(R.string.next)
                    }

                    1 -> {
                        binding.tvTitle.text = getString(R.string.title_two_introduce)
                        binding.tvTitle.setTextColor(ContextCompat.getColor(this@IntroduceActivity, R.color.color_70BAFF))
                        binding.tvDescription.text = getString(R.string.description_two_introduce)
                        binding.btnNext.text = getString(R.string.next)
                    }

                    2 -> {
                        binding.tvTitle.text = getString(R.string.title_three_introduce)
                        binding.tvTitle.setTextColor(ContextCompat.getColor(this@IntroduceActivity, R.color.color_918FF4))
                        binding.tvDescription.text = getString(R.string.description_three_introduce)
                        binding.btnNext.text = getString(R.string.get_started)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}
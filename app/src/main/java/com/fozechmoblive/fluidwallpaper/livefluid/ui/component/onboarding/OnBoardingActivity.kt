package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.onboarding

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ads.control.ads.ITGAd
import com.facebook.shimmer.ShimmerFrameLayout
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityOnboardingBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.GuideModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.onboarding.adapter.OnBoardingAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import timber.log.Timber
import kotlin.math.abs

class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>() {
    private var tutorialAdapter: OnBoardingAdapter? = null
    private var posViewPager = 0

    override fun getLayoutActivity(): Int = R.layout.activity_onboarding

    override fun initViews() {
        mBinding.tvGetStart.text = getString(R.string.next)
        tutorialAdapter = OnBoardingAdapter()
        mBinding.viewPager2.adapter = tutorialAdapter
        mBinding.viewPager2.clipToPadding = false
        mBinding.viewPager2.clipChildren = false
        mBinding.viewPager2.offscreenPageLimit = 3
        mBinding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(100))
        compositePageTransformer.addTransformer { view, position ->
            val r = 1 - abs(position)
            view.scaleY = 0.8f + r * 0.2f
            val absPosition = abs(position)
            view.alpha = 1.0f - (1.0f - 0.3f) * absPosition
        }

        mBinding.viewPager2.setPageTransformer(compositePageTransformer)
        mBinding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            @SuppressLint("InvalidAnalyticsName", "UseCompatLoadingForDrawables")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        posViewPager = 0
                        mBinding.tvGetStart.text = getString(R.string.next)
                        mBinding.view1.setImageResource(R.drawable.ic_view_select)
                        mBinding.view2.setImageResource(R.drawable.ic_view_un_select)
                        mBinding.view3.setImageResource(R.drawable.ic_view_un_select)
                    }

                    1 -> {
                        posViewPager = 1
                        mBinding.tvGetStart.text = getString(R.string.next)
                        mBinding.view1.setImageResource(R.drawable.ic_view_un_select)
                        mBinding.view2.setImageResource(R.drawable.ic_view_select)
                        mBinding.view3.setImageResource(R.drawable.ic_view_un_select)
                    }

                    2 -> {
                        posViewPager = 2
                        mBinding.tvGetStart.text = getString(R.string.get_started)
                        mBinding.view1.setImageResource(R.drawable.ic_view_un_select)
                        mBinding.view2.setImageResource(R.drawable.ic_view_un_select)
                        mBinding.view3.setImageResource(R.drawable.ic_view_select)
                    }
                }
            }
        })

        getData()
    }

    override fun onClickViews() {
        mBinding.textSkip.setOnClickListener {
            gotoNextScreen()
        }
        mBinding.tvGetStart.setOnClickListener {
            when (posViewPager) {
                0 -> {
                    mBinding.viewPager2.currentItem = posViewPager + 1
                    mBinding.view1.setImageResource(R.drawable.ic_view_un_select)
                    mBinding.view2.setImageResource(R.drawable.ic_view_select)
                    mBinding.view3.setImageResource(R.drawable.ic_view_un_select)
                }

                1 -> {
                    mBinding.viewPager2.currentItem = posViewPager + 1
                    mBinding.tvGetStart.text = getString(R.string.get_started)
                    mBinding.view1.setImageResource(R.drawable.ic_view_un_select)
                    mBinding.view2.setImageResource(R.drawable.ic_view_un_select)
                    mBinding.view3.setImageResource(R.drawable.ic_view_select)
                }

                2 -> {
                    gotoNextScreen()
                }
            }
        }
    }

    private fun gotoNextScreen() {
        Routes.startMainActivity(this)
        finish()

    }

    private fun getData() {
        val mHelpGuide: ArrayList<GuideModel> = ArrayList()
        mHelpGuide.add(
            GuideModel(
                R.drawable.ic_onboarding_1,
                R.string.text_title_onboarding_1,
                R.string.text_content_onboarding_1,

                )
        )
        mHelpGuide.add(
            GuideModel(
                R.drawable.ic_onboarding_2,
                R.string.text_title_onboarding_2,
                R.string.text_content_onboarding_2,
            )
        )
        mHelpGuide.add(
            GuideModel(
                R.drawable.ic_onboarding_3,
                R.string.text_title_onboarding_3,
                R.string.text_content_onboarding_3,
            )
        )
        tutorialAdapter?.submitData(mHelpGuide as List<GuideModel>)
    }

}
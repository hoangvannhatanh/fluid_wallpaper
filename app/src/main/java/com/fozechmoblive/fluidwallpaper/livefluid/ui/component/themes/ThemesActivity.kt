package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityThemesBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.ThemeNewUpdateAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.ThemeViewPagerAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.WallpaperAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


@AndroidEntryPoint
class ThemesActivity : BaseActivity<ActivityThemesBinding>() {
    private var listPresetModelTotal = arrayListOf<PresetModel>()

    private lateinit var wallpaperAdapter: WallpaperAdapter
    private lateinit var themeViewPagerAdapter: ThemeViewPagerAdapter
    private lateinit var themeNewUpdateAdapter: ThemeNewUpdateAdapter

    override fun getLayoutActivity(): Int = R.layout.activity_themes

    override fun initViews() {
        super.initViews()
        prefs[AppConstants.KEY_FIRST_ON_BOARDING] = true
        initViewPager()
        setRecyclerViewNewUpdate()
    }

    override fun onClickViews() {
        super.onClickViews()

    }

    private fun initViewPager() {
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPreset())

        themeViewPagerAdapter = ThemeViewPagerAdapter()
        themeViewPagerAdapter.addAll(listPresetModelTotal)
        mBinding.viewPager2.apply {
            adapter = themeViewPagerAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 5
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(20))
            compositePageTransformer.addTransformer { page, position ->
                val r: Float = 1 - abs(position)
                page.scaleY = 0.65f + r * 0.15f
            }
            setPageTransformer(compositePageTransformer)
        }
        mBinding.viewPager2.currentItem = 5
    }

    private fun setRecyclerViewNewUpdate() {
        themeNewUpdateAdapter = ThemeNewUpdateAdapter()
        try {
            mBinding.recyclerViewNewUpdate.apply {
                layoutManager = LinearLayoutManager(
                    this@ThemesActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                themeNewUpdateAdapter.addAll(listPresetModelTotal)
                adapter = themeNewUpdateAdapter
            }
        } catch (_: Exception) {
        }
    }

    private fun moveToPresetActivity(presetModel: PresetModel) {
        Routes.startPresetLiveActivity(this, presetModel)
    }

    override fun onResume() {
        super.onResume()
//        wallpaperAdapter.setCheckNewItem(prefs.getString(AppConstants.KEY_NAME_EFFECT, "") ?: "")
    }
}

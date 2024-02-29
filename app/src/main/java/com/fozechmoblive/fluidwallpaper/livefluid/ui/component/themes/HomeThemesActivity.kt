package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes

import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.callback.CallBack
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityThemesBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.showActivity
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language.LanguageSettingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.ThemeFeatureAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.ThemeNewUpdateAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.ThemeViewPagerAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.WallpaperAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


@AndroidEntryPoint
class HomeThemesActivity : BaseActivity<ActivityThemesBinding>() {
    private var listPresetModelTotal = arrayListOf<PresetModel>()
    private var listNewUpdateTheme = listOf<PresetModel>()
    private var listFeatureTheme = listOf<PresetModel>()

    private lateinit var wallpaperAdapter: WallpaperAdapter
    private val themeViewPagerAdapter by lazy { ThemeViewPagerAdapter() }
    private val themeNewUpdateAdapter by lazy { ThemeNewUpdateAdapter() }
    private val themeFeatureAdapter by lazy { ThemeFeatureAdapter() }

    override fun getLayoutActivity(): Int = R.layout.activity_themes

    override fun initViews() {
        super.initViews()
        prefs[AppConstants.KEY_FIRST_ON_BOARDING] = true
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPreset())
        listNewUpdateTheme = filterThemeWithNewUpdate(CommonData.getListPreset())
        listFeatureTheme = filterThemeWithFeature(CommonData.getListPreset())

        initViewPager()
        setRecyclerViewNewUpdate()
        setRecyclerViewFeature()
    }

    override fun onClickViews() {
        super.onClickViews()
        themeViewPagerAdapter.callBackTheme(object : CallBack.CallBackTheme {
            override fun callBackTheme(presetModel: PresetModel) {
                moveToPresetActivity(presetModel)
            }
        })
        themeNewUpdateAdapter.callBackTheme(object : CallBack.CallBackTheme {
            override fun callBackTheme(presetModel: PresetModel) {
                moveToPresetActivity(presetModel)
            }
        })

        binding.ivDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(
                GravityCompat.START
            )
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}
        })

        binding.layoutContent.ivClose.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutContent.loLanguage.setOnClickListener {
            showActivity(this@HomeThemesActivity, LanguageSettingActivity::class.java)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun initViewPager() {
        themeViewPagerAdapter.addAll(listPresetModelTotal)
        binding.viewPager2.apply {
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
        binding.viewPager2.currentItem = 5
    }

    private fun setRecyclerViewNewUpdate() {
        try {
            binding.recyclerViewNewUpdate.apply {
                layoutManager = LinearLayoutManager(
                    this@HomeThemesActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                themeNewUpdateAdapter.addAll(listNewUpdateTheme)
                adapter = themeNewUpdateAdapter
            }
        } catch (_: Exception) {
        }
    }

    private fun setRecyclerViewFeature() {
        try {
            binding.recyclerViewFeature.apply {
                layoutManager = LinearLayoutManager(
                    this@HomeThemesActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                themeFeatureAdapter.addAll(listFeatureTheme)
                adapter = themeFeatureAdapter
            }
        } catch (_: Exception) {
        }
    }

    private fun moveToPresetActivity(presetModel: PresetModel) {
        Routes.startPresetLiveActivity(this, presetModel)
    }

    private fun filterThemeWithNewUpdate(listTheme: List<PresetModel>): List<PresetModel> {
        return listTheme.filter { it.typePresetModel == TypePresetModel.NEW }
    }

    private fun filterThemeWithFeature(listTheme: List<PresetModel>): List<PresetModel> {
        return listTheme.filter { it.typePresetModel == TypePresetModel.FEATURE }
    }

    override fun onStop() {
        super.onStop()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onResume() {
        super.onResume()
//        wallpaperAdapter.setCheckNewItem(prefs.getString(AppConstants.KEY_NAME_EFFECT, "") ?: "")
    }
}

package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.callback.CallBack
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityHomeThemesBinding
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogThanksYouBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.getPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.onClick
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.showActivity
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.my_theme.MyThemeActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language.LanguageSettingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.policy.PolicyActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.see_all_theme.SeeAllThemeActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter.ThemeFeatureAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter.ThemeNewUpdateAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter.ThemeTrendingAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter.WallpaperAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.setting.SettingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperLiveViewActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.custom_theme.CustomThemeActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.RatingDialog
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


@AndroidEntryPoint
class HomeThemesActivity : BaseActivity<ActivityHomeThemesBinding>() {
    private var listPresetModelTotal = arrayListOf<PresetModel>()
    private var listTrendingTheme = listOf<PresetModel>()
    private var listNewUpdateTheme = listOf<PresetModel>()
    private var listFeatureTheme = listOf<PresetModel>()

    private lateinit var wallpaperAdapter: WallpaperAdapter
    private val themeTrendingAdapter by lazy { ThemeTrendingAdapter() }
    private val themeNewUpdateAdapter by lazy { ThemeNewUpdateAdapter() }
    private val themeFeatureAdapter by lazy { ThemeFeatureAdapter() }

    override fun getLayoutActivity(): Int = R.layout.activity_home_themes

    override fun initViews() {
        super.initViews()
        prefs[AppConstants.KEY_FIRST_ON_BOARDING] = true
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPreset())
        listTrendingTheme = filterThemeWithTrending(CommonData.getListPreset())
        listNewUpdateTheme = filterThemeWithNewUpdate(CommonData.getListPreset())
        listFeatureTheme = filterThemeWithFeature(CommonData.getListPreset())

        setRecyclerViewTrending()
        setRecyclerViewNewUpdate()
        setRecyclerViewFeature()

//        binding.recyclerViewTrending.setPageTransformer { page, position ->
//            val absPosition = Math.abs(position)
//            page.scaleY = 1f - absPosition / 2f
//            page.scaleX = 1f - absPosition / 2f
//        }
    }

    override fun onClickViews() {
        super.onClickViews()
        themeTrendingAdapter.callBackTheme(object : CallBack.CallBackTheme {
            override fun callBackTheme(presetModel: PresetModel) {
                moveToPresetActivity(presetModel)
            }
        })
        themeNewUpdateAdapter.callBackTheme(object : CallBack.CallBackTheme {
            override fun callBackTheme(presetModel: PresetModel) {
                moveToPresetActivity(presetModel)
            }
        })
        themeFeatureAdapter.callBackTheme(object : CallBack.CallBackTheme {
            override fun callBackTheme(presetModel: PresetModel) {
                moveToPresetActivity(presetModel)
            }
        })

        binding.ivDrawer.setOnClickListener {
//            binding.drawerLayout.openDrawer(
//                GravityCompat.START
//            )
            showActivity(this@HomeThemesActivity, SettingActivity::class.java)
        }
//        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
//            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
//
//            override fun onDrawerOpened(drawerView: View) {}
//
//            override fun onDrawerClosed(drawerView: View) {}
//
//            override fun onDrawerStateChanged(newState: Int) {}
//        })
//
//        binding.layoutContent.ivClose.setOnClickListener {
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        }
//
//        binding.layoutContent.loLanguage.onClick(1000) {
//            showActivity(this@HomeThemesActivity, LanguageSettingActivity::class.java)
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        }
//
//        binding.layoutContent.loPolicy.onClick(1000) {
//            showActivity(this@HomeThemesActivity, PolicyActivity::class.java)
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        }
//
//        binding.layoutContent.loRate.onClick(1000) {
//            openRate()
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        }
//
//        binding.layoutContent.loShare.onClick(1000) {
//            shareApp()
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        }

        binding.tvTrendingSeeAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AppConstants.STYLE_THEME, "TRENDING")
            showActivity(this@HomeThemesActivity, SeeAllThemeActivity::class.java, bundle)
        }

        binding.tvNewUpdateSeeAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AppConstants.STYLE_THEME, "NEW_UPDATE")
            showActivity(this@HomeThemesActivity, SeeAllThemeActivity::class.java, bundle)
        }

        binding.tvFeatureSeeAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AppConstants.STYLE_THEME, "FEATURE")
            showActivity(this@HomeThemesActivity, SeeAllThemeActivity::class.java, bundle)
        }

        binding.ivCreateTheme.setOnClickListener {
            Intent(this@HomeThemesActivity, CustomThemeActivity::class.java).apply {
                putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, this@HomeThemesActivity::class.java.simpleName)
                startActivity(this)
            }
        }

        binding.loCustomTheme.setOnClickListener {
            Intent(this@HomeThemesActivity, CustomThemeActivity::class.java).apply {
                putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, this@HomeThemesActivity::class.java.simpleName)
                startActivity(this)
            }
        }

        binding.loMyTheme.setOnClickListener {
            Intent(this@HomeThemesActivity, MyThemeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, this@HomeThemesActivity::class.java.simpleName)
                this@HomeThemesActivity.startActivity(this)
            }
        }
    }

    private fun setRecyclerViewTrending() {
        try {
            binding.recyclerViewTrending.apply {
                layoutManager = LinearLayoutManager(
                    this@HomeThemesActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                themeNewUpdateAdapter.addAll(listTrendingTheme)
                adapter = themeNewUpdateAdapter
            }
        } catch (_: Exception) {
        }
    }

    private fun setRecyclerViewNewUpdate() {
        try {
            binding.recyclerViewNewUpdate.apply {
                layoutManager = LinearLayoutManager(
                    this@HomeThemesActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                themeTrendingAdapter.addAll(listNewUpdateTheme)
                adapter = themeTrendingAdapter
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
        Intent(this@HomeThemesActivity, WallpaperLiveViewActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, this@HomeThemesActivity::class.java.simpleName)
            putExtra(AppConstants.KEY_PRESET_MODEL, presetModel)
            startActivity(this)
        }
    }

    private fun filterThemeWithTrending(listTheme: List<PresetModel>): List<PresetModel> {
        return listTheme.filter { it.typePresetModel == TypePresetModel.TRENDING }
    }

    private fun filterThemeWithNewUpdate(listTheme: List<PresetModel>): List<PresetModel> {
        return listTheme.filter { it.typePresetModel == TypePresetModel.NEW }
    }

    private fun filterThemeWithFeature(listTheme: List<PresetModel>): List<PresetModel> {
        return listTheme.filter { it.typePresetModel == TypePresetModel.FEATURE }
    }

    override fun onStop() {
        super.onStop()
//        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onResume() {
        super.onResume()
//        binding.layoutContent.loRate.isVisible = !getPref(this@HomeThemesActivity, AppConstants.RATED, false)
//        wallpaperAdapter.setCheckNewItem(prefs.getString(AppConstants.KEY_NAME_EFFECT, "") ?: "")
    }
}

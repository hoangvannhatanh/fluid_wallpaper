package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.see_all_theme

import androidx.recyclerview.widget.GridLayoutManager
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.callback.CallBack
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivitySeeAllThemeBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.see_all_theme.adapter.SeeAllThemeAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllThemeActivity : BaseActivity<ActivitySeeAllThemeBinding>() {
    private var strTheme = ""
    private var listPresetModelTotal = arrayListOf<PresetModel>()
    private var listTrendingTheme = listOf<PresetModel>()
    private var listNewUpdateTheme = listOf<PresetModel>()
    private var listFeatureTheme = listOf<PresetModel>()
    private val seeAllThemeAdapter by lazy { SeeAllThemeAdapter() }

    override fun getLayoutActivity() = R.layout.activity_see_all_theme

    override fun initViews() {
        super.initViews()
        strTheme = intent?.extras?.getString(AppConstants.STYLE_THEME) ?: "TRENDING"
        when (strTheme) {
            "TRENDING" -> {
                binding.textTitle.text = getString(R.string.txt_trending)
            }
            "NEW_UPDATE" -> {
                binding.textTitle.text = getString(R.string.new_update)
            }
            "FEATURE" -> {
                binding.textTitle.text = getString(R.string.feature)
            }
        }
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPreset())
        listTrendingTheme = filterThemeWithTrending(CommonData.getListPreset())
        listNewUpdateTheme = filterThemeWithNewUpdate(CommonData.getListPreset())
        listFeatureTheme = filterThemeWithFeature(CommonData.getListPreset())

        setRecyclerView()
    }

    private fun setRecyclerView() {
        try {
            binding.recyclerViewNewUpdate.apply {
                layoutManager = GridLayoutManager(
                    this@SeeAllThemeActivity,
                    2
                )
                when (strTheme) {
                    "TRENDING" -> {
                        seeAllThemeAdapter.addAll(listTrendingTheme)
                    }
                    "NEW_UPDATE" -> {
                        seeAllThemeAdapter.addAll(listNewUpdateTheme)
                    }
                    "FEATURE" -> {
                        seeAllThemeAdapter.addAll(listFeatureTheme)
                    }
                }
                adapter = seeAllThemeAdapter
            }
        } catch (_: Exception) {
        }
    }

    override fun onClickViews() {
        super.onClickViews()
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        seeAllThemeAdapter.callBackTheme(object : CallBack.CallBackTheme {
            override fun callBackTheme(presetModel: PresetModel) {
                moveToPresetActivity(presetModel)
            }
        })
    }

    private fun moveToPresetActivity(presetModel: PresetModel) {
        Routes.startPresetLiveActivity(this, presetModel)
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
}
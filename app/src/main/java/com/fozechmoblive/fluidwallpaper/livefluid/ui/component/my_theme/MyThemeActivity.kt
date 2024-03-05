package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.my_theme

import androidx.recyclerview.widget.GridLayoutManager
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityMyThemeBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter.WallpaperAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyThemeActivity : BaseActivity<ActivityMyThemeBinding>() {
    private var listMyTheme = arrayListOf<PresetModel>()
    private lateinit var wallpaperAdapter: WallpaperAdapter
    override fun getLayoutActivity(): Int = R.layout.activity_my_theme

    override fun initViews() {
        super.initViews()
        loadDataPreset()
    }

    override fun onClickViews() {
        super.onClickViews()
        binding.ivBack.setOnClickListener { onBackPressed() }
    }


    private fun loadDataPreset() {
        wallpaperAdapter = WallpaperAdapter(presetName = prefs.getString(
            AppConstants.KEY_NAME_EFFECT, "Floating Flames"
        ) ?: "", onClickItemSound = { presetModel, position ->
            moveToPresetActivity(presetModel)
        })
        listMyTheme.clear()
        listMyTheme.addAll(CommonData.getListPresetCustom(this@MyThemeActivity))

        binding.rcvMainPreset.apply {
            layoutManager =
                GridLayoutManager(
                    this@MyThemeActivity,
                    3,
                    GridLayoutManager.VERTICAL,
                    false
                )
            setHasFixedSize(true)
            adapter = wallpaperAdapter
        }

        wallpaperAdapter.submitData(listMyTheme)
        wallpaperAdapter.setMList(listMyTheme)

        wallpaperAdapter.filterTypePosition(TypePresetModel.CUSTOM)

    }

    private fun moveToPresetActivity(presetModel: PresetModel) {
        Routes.startPresetLiveActivity(this, presetModel)
    }

    override fun onResume() {
        super.onResume()
        wallpaperAdapter.setCheckNewItem(prefs.getString(AppConstants.KEY_NAME_EFFECT, "") ?: "")
    }

}
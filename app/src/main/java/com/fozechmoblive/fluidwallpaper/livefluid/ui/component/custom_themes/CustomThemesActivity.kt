package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.custom_themes

import androidx.recyclerview.widget.GridLayoutManager
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityCustomThemesBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.models.Status
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.goneView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.isNetwork
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.visibleView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.WallpaperAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomThemesActivity : BaseActivity<ActivityCustomThemesBinding>() {
    private var listPresetModelTotal = arrayListOf<PresetModel>()
    private lateinit var wallpaperAdapter: WallpaperAdapter
    override fun getLayoutActivity(): Int = R.layout.activity_custom_themes

    override fun onResume() {
        super.onResume()
        val listNew = arrayListOf<PresetModel>()
        listNew.clear()
        listNew.addAll(CommonData.getListPresetCustom(this@CustomThemesActivity))
        if (listNew.size > listPresetModelTotal.filter { it.typePresetModel != TypePresetModel.ADS }.size) {
            loadDataPreset()
        }

        wallpaperAdapter.setCheckNewItem(prefs.getString(AppConstants.KEY_NAME_EFFECT, "") ?: "")

    }

    override fun initViews() {
        super.initViews()
        loadDataPreset()
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.apply {

            imvBack.click {
                finish()
            }

            ivAdd.click {
                Routes.startCustomWallpaperActivity(this@CustomThemesActivity)
            }
        }
    }


    private fun loadDataPreset() {
        wallpaperAdapter = WallpaperAdapter(presetName = prefs.getString(
            AppConstants.KEY_NAME_EFFECT, "Floating Flames"
        ) ?: "", onClickItemSound = { presetModel, position ->
            moveToPresetActivity(presetModel)
        })
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPresetCustom(this@CustomThemesActivity))
        addNativeToList(listPresetModelTotal)

        mBinding.rcvMainPreset.apply {
            layoutManager =
                GridLayoutManager(
                    this@CustomThemesActivity,
                    3,
                    GridLayoutManager.VERTICAL,
                    false
                ).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (listPresetModelTotal[position].typePresetModel == TypePresetModel.ADS) 3 else 1 //put your condition here
                        }
                    }
                }
            setHasFixedSize(true)
            adapter = wallpaperAdapter
        }

        wallpaperAdapter.submitData(listPresetModelTotal)
        wallpaperAdapter.setMList(listPresetModelTotal)

        wallpaperAdapter.filterTypePosition(TypePresetModel.CUSTOM)

        if (listPresetModelTotal.isEmpty()) {
            mBinding.layoutEmpty.visibleView()
            mBinding.ivAdd.visibleView()
        } else {
            mBinding.layoutEmpty.goneView()
            mBinding.ivAdd.goneView()
        }

    }

    private fun addNativeToList(listPresetModelTotal: ArrayList<PresetModel>) {
        val itemsToAdd = ArrayList<PresetModel>()

        for (i in 0 until listPresetModelTotal.size) {
            itemsToAdd.add(listPresetModelTotal[i])
            if (isNetwork()) {
                if ((i + 1) % 9 == 0) {
                    val newItem = PresetModel(
                        R.drawable.bg_preset_vip1,
                        "Ads",
                        Status.FREE,
                        false,
                        typePresetModel = TypePresetModel.ADS
                    )
                    itemsToAdd.add(newItem)
                }
            }
        }
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(itemsToAdd)
    }

    private fun moveToPresetActivity(presetModel: PresetModel) {
        Routes.startPresetLiveActivity(this, presetModel)
    }

}
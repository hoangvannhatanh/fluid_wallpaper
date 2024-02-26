package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes

import androidx.recyclerview.widget.GridLayoutManager
import com.ads.control.ads.ITGAd
import com.ads.control.ads.ITGAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager.loadBanner
import com.fozechmoblive.fluidwallpaper.livefluid.ads.CheckTimeShowAdsInter
import com.fozechmoblive.fluidwallpaper.livefluid.ads.RemoteConfigUtils
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityThemesBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.models.Status
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.isNetwork
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showRateDialog
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.WallpaperAdapter
import com.fozechmoblive.fluidwallpaper.livefluid.utils.CommonData
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ThemesActivity : BaseActivity<ActivityThemesBinding>() {
    private var listPresetModelTotal = arrayListOf<PresetModel>()
    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun getLayoutActivity(): Int = R.layout.activity_themes

    override fun onResume() {
        super.onResume()
        val listNew = arrayListOf<PresetModel>()
        listNew.clear()
        listNew.addAll(CommonData.getListPreset())
        if (listNew.size > listPresetModelTotal.filter { it.typePresetModel != TypePresetModel.ADS }.size) {
            loadDataPreset()
        }

        wallpaperAdapter.setCheckNewItem(prefs.getString(AppConstants.KEY_NAME_EFFECT, "") ?: "")

    }

    override fun initViews() {
        prefs[AppConstants.KEY_SELECT_LANGUAGE] = true
        prefs[AppConstants.KEY_FIRST_ON_BOARDING] = true

        loadDataPreset()
        if (intent.extras?.getBoolean(AppConstants.KEY_SET_WALLPAPER_SUCCESS, false) == true) {
            if (!SharePrefUtils.getBoolean(
                    AppConstants.IS_RATED,
                    false
                ) && !SharePrefUtils.getBoolean(AppConstants.IS_FIRST_RATED, false)
            ) {
                if (RemoteConfigUtils.getIsShowRate()) {
                    showRateDialog(this@ThemesActivity, false)
                    SharePrefUtils.putBoolean(AppConstants.IS_FIRST_RATED, true)

                } else {
                    showSuccessDialog()


                }
            } else {
                showSuccessDialog()
            }
        }
        super.initViews()
        initAdsBanner()
        initAdapterPager()

        AdsManager.loadInterWallpaper(this)
        AdsManager.loadNativeTheme(this)
        AdsManager.loadNativeLoading(this)
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.imvBack.click {
            finish()
        }
    }


    private fun moveToPresetActivity(presetModel: PresetModel) {
        Routes.startPresetLiveActivity(this, presetModel)
    }

    private fun initAdapterPager() {
        val titles = listOf(
            getString(R.string.txt_trending),
            getString(R.string.txt_new),
            getString(
                R.string.txt_hot
            )
        )

        for (title in titles) {
            val tab: TabLayout.Tab = mBinding.tabMain.newTab()
            tab.text = title
            mBinding.tabMain.addTab(tab)
        }




        mBinding.tabMain.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    1 -> {
                        wallpaperAdapter.filterTypePosition(TypePresetModel.TRENDING)
                    }

                    2 -> {
                        wallpaperAdapter.filterTypePosition(TypePresetModel.NEW)
                    }


                    3 -> {
                        wallpaperAdapter.filterTypePosition(TypePresetModel.HOT)
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        mBinding.tabMain.getTabAt(0)?.select()

    }

    private fun initAdsBanner() {
        loadBanner(
            this,
            BuildConfig.admob_banner_all,
            mBinding.frBanner,
            RemoteConfigUtils.getOnBannerAll()
        )

    }


    private fun loadDataPreset() {
        wallpaperAdapter = WallpaperAdapter(presetName = prefs.getString(
            AppConstants.KEY_NAME_EFFECT, "Floating Flames"
        ) ?: "", onClickItemSound = { presetModel, position ->


            if (AdsManager.mInterstitialAdWallpaper != null && CheckTimeShowAdsInter.isTimeShow && AdsManager.mInterstitialAdWallpaper!!.isReady) {

                ITGAd.getInstance().forceShowInterstitial(
                    this, AdsManager.mInterstitialAdWallpaper, object : ITGAdCallback() {
                        override fun onAdFailedToLoad(adError: ApAdError?) {
                            super.onAdFailedToLoad(adError)
                            moveToPresetActivity(presetModel)
                            AdsManager.mInterstitialAdWallpaper = null
                            AdsManager.loadInterWallpaper(this@ThemesActivity)
                        }

                        override fun onAdClosed() {
                            super.onAdClosed()
                            CheckTimeShowAdsInter.logShowed()
                            moveToPresetActivity(presetModel)
                            AdsManager.mInterstitialAdWallpaper = null
                            AdsManager.loadInterWallpaper(this@ThemesActivity)
                        }

                        override fun onAdFailedToShow(adError: ApAdError?) {
                            super.onAdFailedToShow(adError)
                            moveToPresetActivity(presetModel)
                            AdsManager.mInterstitialAdWallpaper = null
                            AdsManager.loadInterWallpaper(this@ThemesActivity)
                        }
                    }, true
                )

            } else {
                moveToPresetActivity(presetModel)
            }

        })
        listPresetModelTotal.clear()
        listPresetModelTotal.addAll(CommonData.getListPreset())
        addNativeToList(listPresetModelTotal)

        mBinding.rcvMainPreset.apply {
            layoutManager =
                GridLayoutManager(
                    this@ThemesActivity,
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

        wallpaperAdapter.filterTypePosition(TypePresetModel.TRENDING)

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
}

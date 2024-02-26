package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.main

import com.ads.control.ads.ITGAd
import com.ads.control.ads.ITGAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.facebook.shimmer.ShimmerFrameLayout
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager
import com.fozechmoblive.fluidwallpaper.livefluid.ads.CheckTimeShowAdsInter
import com.fozechmoblive.fluidwallpaper.livefluid.ads.PreLoadNativeListener
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityMainBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.goneView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.visibleView
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), PreLoadNativeListener {

    override fun getLayoutActivity(): Int = R.layout.activity_main
    private var populateNativeAdView = false
    private var shimmerSmall: ShimmerFrameLayout? = null

    override fun initViews() {
        super.initViews()
        shimmerSmall = findViewById(R.id.shimmer_native_large)

        AdsManager.loadNativeWallpaper(this)
        AdsManager.loadInterHome(this)

        AdsManager.setPreLoadNativeCallbackHome(this)
        showNativeHome()

    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.apply {
            btnThemes.click {

                if (AdsManager.mInterstitialAdHome != null && AdsManager.mInterstitialAdHome!!.isReady && CheckTimeShowAdsInter.isTimeShow) {

                    ITGAd.getInstance().forceShowInterstitial(
                        this@MainActivity,
                        AdsManager.mInterstitialAdHome,
                        object : ITGAdCallback() {
                            override fun onAdFailedToLoad(adError: ApAdError?) {
                                super.onAdFailedToLoad(adError)
                                Routes.startThemesActivity(this@MainActivity)
                                AdsManager.mInterstitialAdHome = null
                                AdsManager.loadInterHome(this@MainActivity)
                            }

                            override fun onAdClosed() {
                                super.onAdClosed()
                                CheckTimeShowAdsInter.logShowed()
                                Routes.startThemesActivity(this@MainActivity)
                                AdsManager.mInterstitialAdHome = null
                                AdsManager.loadInterHome(this@MainActivity)

                            }

                            override fun onAdFailedToShow(adError: ApAdError?) {
                                super.onAdFailedToShow(adError)
                                Routes.startThemesActivity(this@MainActivity)
                                AdsManager.mInterstitialAdHome = null
                                AdsManager.loadInterHome(this@MainActivity)

                            }
                        },
                        true
                    )

                } else {
                    Routes.startThemesActivity(this@MainActivity)

                }
            }

            btnCustomThemes.click {
                if (AdsManager.mInterstitialAdHome != null && AdsManager.mInterstitialAdHome!!.isReady && CheckTimeShowAdsInter.isTimeShow) {

                    ITGAd.getInstance().forceShowInterstitial(
                        this@MainActivity,
                        AdsManager.mInterstitialAdHome,
                        object : ITGAdCallback() {
                            override fun onAdFailedToLoad(adError: ApAdError?) {
                                super.onAdFailedToLoad(adError)
                                Routes.startCustomThemesActivity(this@MainActivity)
                                AdsManager.mInterstitialAdHome = null
                                AdsManager.loadInterHome(this@MainActivity)
                            }

                            override fun onAdClosed() {
                                super.onAdClosed()
                                CheckTimeShowAdsInter.logShowed()
                                Routes.startCustomThemesActivity(this@MainActivity)
                                AdsManager.mInterstitialAdHome = null
                                AdsManager.loadInterHome(this@MainActivity)
                            }

                            override fun onAdFailedToShow(adError: ApAdError?) {
                                super.onAdFailedToShow(adError)
                                Routes.startCustomThemesActivity(this@MainActivity)
                                AdsManager.mInterstitialAdHome = null
                                AdsManager.loadInterHome(this@MainActivity)
                            }
                        },
                        true
                    )

                } else {
                    Routes.startCustomThemesActivity(this@MainActivity)

                }
            }

            btnSettings.click {
                Routes.startSettingActivity(this@MainActivity)
            }
        }
    }


    private fun showNativeHome() {
        if (AdsManager.nativeAdHome != null && !populateNativeAdView) {
            Timber.e("initAdmob: ${AdsManager.nativeAdHome}")
            mBinding.frAds.visibleView()
            populateNativeAdView = true
            ITGAd.getInstance().populateNativeAdView(
                this, AdsManager.nativeAdHome, mBinding.frAds, shimmerSmall
            )
        } else {
            mBinding.frAds.goneView()
            Timber.d(
                "LanguageActivity initAds nativeAdViewLanguage = ${AdsManager.nativeAdHome} - nativeAdHome = ${AdsManager.nativeAdHome}"
            )
        }
    }

    override fun onLoadNativeSuccess() {

        showNativeHome()
    }

    override fun onLoadNativeFail() {
        if (AdsManager.nativeAdHome != null) {
            mBinding.frAds.visibleView()
        } else {
            mBinding.frAds.goneView()
        }
    }

}
package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.splash

import android.os.CountDownTimer
import android.util.Log
import com.ads.control.ads.ITGAd
import com.ads.control.ads.ITGAdCallback
import com.bumptech.glide.Glide
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager.loadNativeLanguage
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager.loadNativeOnBoarding
import com.fozechmoblive.fluidwallpaper.livefluid.ads.RemoteConfigUtils
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants.KEY_FIRST_ON_BOARDING
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants.KEY_SELECT_LANGUAGE
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivitySplashBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.get
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(), RemoteConfigUtils.Listener {
    private var getConfigSuccess = false
    private var selectLanguage = false
    private var selectOnBoarding = false

    companion object {
        var checkMoveToLanguages: Boolean = false
    }

    override fun getLayoutActivity() = R.layout.activity_splash

    override fun initViews() {
        super.initViews()
//        MobileAds.initialize(this) {}
        Glide.with(this).load(R.drawable.logo_app).into(mBinding.imgSplash)
        selectLanguage = prefs[KEY_SELECT_LANGUAGE, false] == true
        selectOnBoarding = prefs[KEY_FIRST_ON_BOARDING, false] == true
//        RemoteConfigUtils.init(this)
//        loadingRemoteConfig()
        checkMoveToLanguages = false
        Routes.startLanguageActivity(this, null)
        finish()
    }

    private fun loadingRemoteConfig() {
        object : CountDownTimer(AppConstants.DEFAULT_TIME_SPLASH, 100) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("SplashActivity", " loadingRemoteConfig onTick == $millisUntilFinished")
                if (getConfigSuccess && millisUntilFinished < AppConstants.DEFAULT_LIMIT_TIME_SPLASH) {
                    checkRemoteConfigResult()
                    cancel()
                }
            }

            override fun onFinish() {
                if (!getConfigSuccess) {
                    checkRemoteConfigResult()
                }
            }
        }.start()
    }


    private fun checkRemoteConfigResult() {
        // check if not select language => show language screen
        if (!selectLanguage) {
            loadNativeLanguage(this, true)
        } else {
            loadNativeLanguage(this, false)
        }
        // check if not select onBoarding => show onBoarding screen
        if (!selectOnBoarding) {
            loadNativeOnBoarding(this, true)
        } else {
            loadNativeOnBoarding(this, false)
        }

        if (RemoteConfigUtils.getOnInterSplash()) {
            ITGAd.getInstance().loadSplashInterstitialAds(
                this,
                BuildConfig.admob_inter_splash,
                20000,
                5000,
                object : ITGAdCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        moveActivity()
                    }
                })
        } else {
            moveActivity()
        }
    }


    private fun moveActivity() {
        if (!checkMoveToLanguages) {
            checkMoveToLanguages = true
            Routes.startLanguageActivity(this, null)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
//        ITGAd.getInstance()
//            .onCheckShowSplashWhenFail(this@SplashActivity, object : ITGAdCallback() {
//                override fun onNextAction() {
//                    super.onNextAction()
//                    moveActivity()
//                }
//            }, 1000)
    }


    override fun loadSuccess() {
        getConfigSuccess = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
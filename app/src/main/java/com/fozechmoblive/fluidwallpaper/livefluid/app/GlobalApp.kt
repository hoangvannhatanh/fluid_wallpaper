package com.fozechmoblive.fluidwallpaper.livefluid.app

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import com.ads.control.admob.Admob
import com.ads.control.admob.AppOpenManager
import com.ads.control.ads.ITGAd
import com.ads.control.application.AdsMultiDexApplication
import com.ads.control.applovin.AppLovin
import com.ads.control.applovin.AppOpenMax
import com.ads.control.config.AdjustConfig
import com.ads.control.config.ITGAdConfig
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language.LanguageActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language.LanguageModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.splash.SplashActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SystemUtil
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class GlobalApp : AdsMultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: GlobalApp

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initAds()
        SharePrefUtils.init(this)

    }

    private fun initAds() {
        val environment =
            if (BuildConfig.DEBUG) ITGAdConfig.ENVIRONMENT_DEVELOP else ITGAdConfig.ENVIRONMENT_PRODUCTION
        itgAdConfig = ITGAdConfig(this, ITGAdConfig.PROVIDER_ADMOB, environment)

        // Optional: setup Adjust event
        val adjustConfig = AdjustConfig(true, resources.getString(R.string.adjust_token))
        itgAdConfig.adjustConfig = adjustConfig
        itgAdConfig.facebookClientToken = resources.getString(R.string.facebook_client_token)
        itgAdConfig.adjustTokenTiktok = resources.getString(R.string.event_token)

        // Optional: setup Appsflyer event
//        AppsflyerConfig appsflyerConfig = new AppsflyerConfig(true,APPSFLYER_TOKEN);
//        aperoAdConfig.setAppsflyerConfig(appsflyerConfig);

        // Optional: enable ads resume
        itgAdConfig.idAdResume = BuildConfig.admob_open_resume

        ITGAd.getInstance().init(this, itgAdConfig, false)

        // Auto disable ad resume after user click ads and back to app
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)
        AppLovin.getInstance().setDisableAdResumeWhenClickAds(true)
        // If true -> onNextAction() is called right after Ad Interstitial showed
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)


        if (ITGAd.getInstance().mediationProvider == ITGAdConfig.PROVIDER_ADMOB) {
            AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
            AppOpenManager.getInstance().disableAppResumeWithActivity(LanguageActivity::class.java)
            AppOpenManager.getInstance().disableAppResumeWithActivity(WallpaperActivity::class.java)
            AppOpenManager.getInstance()
        } else {
            AppOpenMax.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
            AppOpenManager.getInstance().disableAppResumeWithActivity(WallpaperActivity::class.java)

        }
    }

    fun getLanguage(): LanguageModel? {
        var languageModel: LanguageModel? = null
        val lang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0].language
        } else {
            Resources.getSystem().configuration.locale.language
        }
        val key = if (!SystemUtil.languageApp.contains(lang)) {
            ""
        } else {
            lang
        }
        for (model in getListLanguageApp()) {
            if (key == model?.isoLanguage) {
                languageModel = model
                break
            }
        }
        return languageModel
    }

    private fun getListLanguageApp(): ArrayList<LanguageModel?> {
        val lists: ArrayList<LanguageModel?> = arrayListOf()
        lists.add(LanguageModel("Hindi", "hi", false, R.drawable.ic_hindi))
        lists.add(LanguageModel("Arabic", "ar", false, R.drawable.ic_arabic))
        lists.add(LanguageModel("Spanish", "es", false, R.drawable.ic_spanish))
        lists.add(LanguageModel("Croatian", "hr", false, R.drawable.ic_croatia))
        lists.add(LanguageModel("Czech", "cs", false, R.drawable.ic_czech_republic))
        lists.add(LanguageModel("Dutch", "nl", false, R.drawable.ic_dutch))
        lists.add(LanguageModel("English", "en", false, R.drawable.ic_english))
        lists.add(LanguageModel("Filipino", "fil", false, R.drawable.ic_filipino))
        lists.add(LanguageModel("French", "fr", false, R.drawable.ic_france))
        lists.add(LanguageModel("German", "de", false, R.drawable.ic_german))
        lists.add(LanguageModel("Indonesian", "in", false, R.drawable.ic_indonesian))
        lists.add(LanguageModel("Italian", "it", false, R.drawable.ic_italian))
        lists.add(LanguageModel("Japanese", "ja", false, R.drawable.ic_japanese))
        lists.add(LanguageModel("Korean", "ko", false, R.drawable.ic_korean))
        lists.add(LanguageModel("Malay", "ms", false, R.drawable.ic_malay))
        lists.add(LanguageModel("Polish", "pl", false, R.drawable.ic_polish))
        lists.add(LanguageModel("Portuguese", "pt", false, R.drawable.ic_portugal))
        lists.add(LanguageModel("Russian", "ru", false, R.drawable.ic_russian))
        lists.add(LanguageModel("Serbian", "sr", false, R.drawable.ic_serbian))
        lists.add(LanguageModel("Swedish", "sv", false, R.drawable.ic_swedish))
        lists.add(LanguageModel("Turkish", "tr", false, R.drawable.ic_turkish))
        lists.add(LanguageModel("Vietnamese", "vi", false, R.drawable.ic_vietnamese))
        lists.add(LanguageModel("China", "zh", false, R.drawable.ic_china))
        return lists
    }
}
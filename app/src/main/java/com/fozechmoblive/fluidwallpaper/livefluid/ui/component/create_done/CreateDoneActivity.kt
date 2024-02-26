package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.create_done
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ads.control.ads.ITGAd
import com.ads.control.ads.ITGAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.bumptech.glide.Glide
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager
import com.fozechmoblive.fluidwallpaper.livefluid.ads.CheckTimeShowAdsInter
import com.fozechmoblive.fluidwallpaper.livefluid.ads.RemoteConfigUtils
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityCreateDoneBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import com.magicfluids.Config

class CreateDoneActivity : BaseActivity<ActivityCreateDoneBinding>() {

    private var presetModel: PresetModel? = null
    private val intentFilter = IntentFilter(AppConstants.ACTION_DESTROY_WALLPAPER_SERVICE)

    override fun getLayoutActivity(): Int = R.layout.activity_create_done


    private val serviceDestroyedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == AppConstants.ACTION_DESTROY_WALLPAPER_SERVICE) {
                showSuccessDialog()

                if (presetModel?.name?.isNotEmpty()!!) {
                    prefs[AppConstants.KEY_NAME_EFFECT] = presetModel?.name
                }
                sendLocalBroadcast()
            }
        }
    }

    override fun initViews() {
        super.initViews()
        registerReceiver(serviceDestroyedReceiver, intentFilter)

        if (intent.hasExtra(AppConstants.KEY_PRESET_MODEL)) {
            presetModel =
                intent.getParcelableExtra<PresetModel>(AppConstants.KEY_PRESET_MODEL) as PresetModel

            Glide.with(this).load(presetModel?.pathImageCustom).into(mBinding.imvPreset)
        }

        initAdsBanner()
        AdsManager.loadInterSetWallpaper(this@CreateDoneActivity)
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.imvBack.setOnClickListener {
            finish()
        }

        mBinding.imvGotoHome.setOnClickListener {
            Routes.startMainActivity(this)
        }

        mBinding.txtMakeAnother.setOnClickListener {
            Routes.startMainActivity(this)
        }

        mBinding.txtSetWallpaper.setOnClickListener {
            applyWallpaper()
        }


    }

    private fun applyWallpaper() {
        if (AdsManager.mInterstitialAdSetWallpaper != null && AdsManager.mInterstitialAdSetWallpaper!!.isReady && CheckTimeShowAdsInter.isTimeShow) {

            ITGAd.getInstance().forceShowInterstitial(
                this@CreateDoneActivity,
                AdsManager.mInterstitialAdSetWallpaper,
                object : ITGAdCallback() {
                    override fun onAdFailedToLoad(adError: ApAdError?) {
                        super.onAdFailedToLoad(adError)
                        applyCurrentSettingsToLwp()
                        setLiveWallpaper()
                        AdsManager.mInterstitialAdSetWallpaper = null
                        AdsManager.loadInterSetWallpaper(this@CreateDoneActivity)
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        CheckTimeShowAdsInter.logShowed()
                        applyCurrentSettingsToLwp()
                        setLiveWallpaper()
                        AdsManager.mInterstitialAdSetWallpaper = null
                        AdsManager.loadInterSetWallpaper(this@CreateDoneActivity)
                    }

                    override fun onAdFailedToShow(adError: ApAdError?) {
                        super.onAdFailedToShow(adError)
                        applyCurrentSettingsToLwp()
                        setLiveWallpaper()
                        AdsManager.mInterstitialAdSetWallpaper = null
                        AdsManager.loadInterSetWallpaper(this@CreateDoneActivity)
                    }
                },
                true
            )

        } else {
            applyCurrentSettingsToLwp()
            setLiveWallpaper()
        }
    }

    private fun applyCurrentSettingsToLwp() {
        applySettingsToLwp(true, -1)

    }

    private fun applySettingsToLwp(z: Boolean, i: Int) {
        if (z) {
            Config.LWPCurrent.copyValuesFrom(Config.Current)
        } else {
            com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadConfigFromInternalPreset(
                presetModel?.name,
                assets,
                Config.LWPCurrent
            )

        }
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.saveSessionConfig(
            this, Config.LWPCurrent, com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.SETTINGS_LWP_NAME
        )
        Config.LWPCurrent.ReloadRequired = true
        Config.LWPCurrent.ReloadRequiredPreview = true

    }

    private fun setLiveWallpaper() {
        try {
            val componentName = ComponentName(
                packageName, com.fozechmoblive.fluidwallpaper.livefluid.services.WallpaperService::class.java.name
            )
            val intent = Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER")
            intent.putExtra(
                "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", componentName
            )
            startActivity(intent)
        } catch (unused: Exception) {
            Toast.makeText(this, "error:" + getText(R.string.not_supported), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initAdsBanner() {
        if (RemoteConfigUtils.getOnBannerAll()) {
            ITGAd.getInstance()
                .loadBanner(this, BuildConfig.admob_banner_all, object : ITGAdCallback() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()

                    }

                    override fun onAdFailedToLoad(adError: ApAdError?) {
                        super.onAdFailedToLoad(adError)
                        mBinding.frBanner.removeAllViews()
                    }
                })
        } else mBinding.frBanner.removeAllViews()
    }

    private fun sendLocalBroadcast() {
        prefs[AppConstants.KEY_NAME_EFFECT] = presetModel?.name
        val intent = Intent(AppConstants.ACTION_CHANGE_DATA)
        intent.putExtra(AppConstants.KEY_CHANGE_DATA, true)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(serviceDestroyedReceiver)

    }


}
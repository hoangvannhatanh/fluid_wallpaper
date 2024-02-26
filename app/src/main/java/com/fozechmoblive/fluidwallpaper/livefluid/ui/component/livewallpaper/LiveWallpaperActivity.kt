package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.livewallpaper

import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityLwpBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.magicfluids.Config
import com.magicfluids.NativeInterface

class LiveWallpaperActivity : BaseActivity<ActivityLwpBinding>() {
    private var ntv: NativeInterface? = null

    private fun initSettings() {
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.QualitySetting.init()
        com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.loadSessionConfig(
            this,
            Config.LWPCurrent,
            com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.fluids.SettingsStorage.SETTINGS_LWP_NAME
        )

    }
    override fun initViews() {
        super.initViews()
        if (com.fozechmoblive.fluidwallpaper.livefluid.services.WallpaperService.mostRecentEngine != null) {
            ntv = com.fozechmoblive.fluidwallpaper.livefluid.services.WallpaperService.mostRecentEngine.ntv
        } else {
            initSettings()
        }
        setContentView(R.layout.activity_lwp)

    }


    override fun getLayoutActivity(): Int = R.layout.activity_lwp


}
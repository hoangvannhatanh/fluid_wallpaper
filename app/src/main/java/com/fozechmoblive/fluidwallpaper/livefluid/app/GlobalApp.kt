package com.fozechmoblive.fluidwallpaper.livefluid.app

import android.app.Application
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SharePrefUtils.init(this)
    }
}
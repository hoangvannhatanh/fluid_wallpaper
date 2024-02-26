package com.fozechmoblive.fluidwallpaper.livefluid.utils

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Bundle
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.create_done.CreateDoneActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.custom_themes.CustomThemesActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language.LanguageActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.main.MainActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.onboarding.OnBoardingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.setting.SettingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.ThemesActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperLiveViewActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.custom.CustomWallpaperActivity


object Routes {
    fun startCustomThemesActivity(fromActivity: Activity) =
        Intent(fromActivity, CustomThemesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }
    fun startThemesActivity(fromActivity: Activity) =
        Intent(fromActivity, ThemesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startMainActivity(fromActivity: Activity) =
        Intent(fromActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startMainActivity(fromActivity: Service, bundle: Bundle?) =
        Intent(fromActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            bundle?.let { putExtras(it) }
            fromActivity.startActivity(this)
        }

    fun startSettingActivity(fromActivity: Activity) =
        Intent(fromActivity, SettingActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }


    fun startOnBoardingActivity(fromActivity: Activity) =
        Intent(fromActivity, OnBoardingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)

        }

    fun startLanguageActivity(fromActivity: Activity, bundle: Bundle?) =
        Intent(fromActivity, LanguageActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            bundle?.let { putExtras(it) }
            fromActivity.startActivity(this)

        }


    fun startPresetActivity(fromActivity: Activity, presetModel: PresetModel, isCustom: Boolean) =
        Intent(fromActivity, WallpaperActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            putExtra(AppConstants.KEY_PRESET_MODEL, presetModel)
            putExtra(AppConstants.KEY_IS_CUSTOM, isCustom)
            fromActivity.startActivity(this)
        }

    fun startPresetLiveActivity(fromActivity: Activity, presetModel: PresetModel) =
        Intent(fromActivity, WallpaperLiveViewActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            putExtra(AppConstants.KEY_PRESET_MODEL, presetModel)
            fromActivity.startActivity(this)

        }

    fun startCustomWallpaperActivity(fromActivity: Activity) =
        Intent(fromActivity, CustomWallpaperActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)

        }

    fun startCreateDoneActivity(fromActivity: Activity, presetModel: PresetModel) =
        Intent(fromActivity, CreateDoneActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            putExtra(AppConstants.KEY_PRESET_MODEL, presetModel)
            fromActivity.startActivity(this)

        }
}
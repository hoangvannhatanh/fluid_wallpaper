package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.main

import android.app.Activity
import android.content.Intent
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityMainBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.custom_themes.CustomThemesActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.setting.SettingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.ThemesActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutActivity(): Int = R.layout.activity_main

    override fun initViews() {
        super.initViews()
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.apply {
            btnThemes.click {
                startThemesActivity(this@MainActivity)
            }

            btnCustomThemes.click {
                startCustomThemesActivity(this@MainActivity)
            }

            btnSettings.click {
                startSettingActivity(this@MainActivity)
            }
        }
    }

    private fun startThemesActivity(fromActivity: Activity) =
        Intent(fromActivity, ThemesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }
    private fun startCustomThemesActivity(fromActivity: Activity) =
        Intent(fromActivity, CustomThemesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    private fun startSettingActivity(fromActivity: Activity) =
        Intent(fromActivity, SettingActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

}
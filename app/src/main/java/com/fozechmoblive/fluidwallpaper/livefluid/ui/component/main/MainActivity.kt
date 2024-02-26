package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.main

import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityMainBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
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
                Routes.startThemesActivity(this@MainActivity)
            }

            btnCustomThemes.click {
                Routes.startCustomThemesActivity(this@MainActivity)
            }

            btnSettings.click {
                Routes.startSettingActivity(this@MainActivity)
            }
        }
    }

}
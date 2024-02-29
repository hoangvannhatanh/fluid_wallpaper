package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.setting

import android.annotation.SuppressLint
import android.widget.Toast
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivitySettingBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showRateDialog
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override fun getLayoutActivity(): Int = R.layout.activity_setting

    override fun onClickViews() {
        super.onClickViews()

        binding.imvBackSettings.setOnClickListener {
            finish()
        }
        binding.layoutShare.click {

        }

        binding.layoutMoreApp.setOnClickListener {
            if (!SharePrefUtils.getBoolean(AppConstants.IS_RATED, false)) {
                showRateDialog(this, false)
            } else {
                Toast.makeText(this, getString(R.string.text_thank_you), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    override fun initViews() {
        super.initViews()
        binding.textVersion.text =
            resources.getString(R.string.txt_version, BuildConfig.VERSION_NAME)
    }
}
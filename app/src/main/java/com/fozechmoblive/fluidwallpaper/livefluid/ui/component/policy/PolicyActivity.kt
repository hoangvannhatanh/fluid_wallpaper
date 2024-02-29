package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.policy

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityPolicyBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.onClick
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyActivity : BaseActivity<ActivityPolicyBinding>() {

    override fun getLayoutActivity() = R.layout.activity_policy

    override fun initViews() {
        super.initViews()

    }

    override fun onClickViews() {
        super.onClickViews()
        binding.tvVersion.text = getString(R.string.version) + " " + BuildConfig.VERSION_NAME

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvTerm.onClick(1000) {
            openUrl("https://ntd-technology.web.app/policy.html")
        }
    }

    private fun openUrl(strUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUrl))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        try {
            startActivity(intent)
        } catch (e: android.content.ActivityNotFoundException) {
            Log.d("", e.toString())
        }
    }
}
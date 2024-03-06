package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivitySettingBinding
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogThanksYouBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.getPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.onClick
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.showActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showRateDialog
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language.LanguageSettingActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.policy.PolicyActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.RatingDialog
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override fun getLayoutActivity(): Int = R.layout.activity_setting

    override fun onClickViews() {
        super.onClickViews()

        binding.ivClose.setOnClickListener {
            onBackPressed()
        }

        binding.loLanguage.onClick(1000) {
            showActivity(this@SettingActivity, LanguageSettingActivity::class.java)
        }

        binding.loPolicy.onClick(1000) {
            showActivity(this@SettingActivity, PolicyActivity::class.java)
        }

        binding.loRate.onClick(1000) {
            openRate()
        }

        binding.loShare.onClick(1000) {
            shareApp()
        }
    }

    @SuppressLint("StringFormatInvalid")
    override fun initViews() {
        super.initViews()

    }



    private fun openRate() {
        val dialogRating = RatingDialog(this)
        dialogRating.init(object : RatingDialog.OnPress {
            override fun send() {
                hideNavigation(dialogRating)
                dialogRating.dismiss()
                setPref(this@SettingActivity, AppConstants.RATED, true)
                binding.loRate.isVisible = !getPref(this@SettingActivity, AppConstants.RATED, false)

                showPopupThankYou()
            }

            override fun rating() {
                val manager: ReviewManager = ReviewManagerFactory.create(this@SettingActivity)
                val request: Task<ReviewInfo> = manager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val reviewInfo: ReviewInfo = task.result
                        val flow: Task<Void> =
                            manager.launchReviewFlow(this@SettingActivity, reviewInfo)
                        flow.addOnCompleteListener {
                            setPref(this@SettingActivity, AppConstants.RATED, true)
                            binding.loRate.isVisible = !getPref(this@SettingActivity, AppConstants.RATED, false)
                            hideNavigation(dialogRating)
                            dialogRating.dismiss()
                        }
                    }
                }
            }

            override fun cancel() {
            }

            override fun later() {
                hideNavigation(dialogRating)
                dialogRating.dismiss()
            }

        })
        dialogRating.setCancelable(false)
        hideNavigation(dialogRating)
        dialogRating.show()
    }

    private fun showPopupThankYou() {
        val dialogPopupThanksYou = Dialog(this@SettingActivity)
        hideNavigation(dialogPopupThanksYou)
        val thanksYouBinding = DialogThanksYouBinding.inflate(LayoutInflater.from(this@SettingActivity))
        dialogPopupThanksYou.setContentView(thanksYouBinding.root)
        val window = dialogPopupThanksYou.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window?.attributes
        windowAttributes?.gravity = Gravity.CENTER

        thanksYouBinding.tvOk.setOnClickListener {
            dialogPopupThanksYou.dismiss()
            hideNavigation(dialogPopupThanksYou)

        }
        dialogPopupThanksYou.setCancelable(true)
        dialogPopupThanksYou.show()
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        val shareMessage =
            "${getString(R.string.app_name)} \n https://play.google.com/store/apps/details?id=${packageName}"

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)))
    }

    private fun hideNavigation(dialog: Dialog) {
        dialog.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun onResume() {
        super.onResume()
        binding.loRate.isVisible = !getPref(this@SettingActivity, AppConstants.RATED, false)
    }
}
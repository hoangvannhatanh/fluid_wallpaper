package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.dialog

import android.app.Activity
import android.widget.Toast
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogRatingAppBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseDialog

class DialogRateApp(
    var activity: Activity,
    var onRating: () -> Unit,
) : BaseDialog<DialogRatingAppBinding>(activity) {

    override fun getLayoutDialog(): Int = R.layout.dialog_rating_app

    override fun initViews() {
        super.initViews()
        mBinding.simpleRatingBar.rating = 5F

    }
    override fun onClickViews() {
        mBinding.tvRateNow.setOnClickListener {
            if (mBinding.simpleRatingBar.rating == 0f) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.please_feedback),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (mBinding.simpleRatingBar.rating <= 3.0) {
                Toast.makeText(activity, R.string.thank_for_your_feedback, Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            } else {
                onRating.invoke()
                dismiss()
            }
        }
    }
}
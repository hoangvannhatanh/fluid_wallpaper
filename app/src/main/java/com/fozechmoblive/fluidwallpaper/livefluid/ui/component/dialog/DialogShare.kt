package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.dialog

import android.content.Context
import android.graphics.Bitmap
import com.fozechmoblive.fluidwallpaper.livefluid.R

import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogShareImageBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseDialog

class DialogShare(context: Context, private val bitmap: Bitmap?) :
    BaseDialog<DialogShareImageBinding>(context) {
    override fun getLayoutDialog(): Int = R.layout.dialog_share_image
    override fun initViews() {
        super.initViews()
        if (bitmap != null) {
            mBinding.imageShare.setImageBitmap(bitmap)

        }
    }

    override fun onClickViews() {
        super.onClickViews()
        mBinding.imvClose.setOnClickListener {
            dismiss()
        }
    }

}
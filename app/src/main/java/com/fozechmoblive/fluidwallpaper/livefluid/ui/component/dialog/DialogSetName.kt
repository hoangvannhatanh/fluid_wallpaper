package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.dialog

import android.app.Activity
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogSetNameBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseDialog
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showKeyboard

class DialogSetName(val context: Activity, val onCreatePreset: (String) -> Unit) :
    BaseDialog<DialogSetNameBinding>(context) {
    override fun getLayoutDialog(): Int = R.layout.dialog_set_name

    override fun initViews() {
        super.initViews()
        context.showKeyboard(mBinding.edtFileName)

    }
    override fun onClickViews() {
        super.onClickViews()

        mBinding.tvCreate.setOnClickListener {

            if (mBinding.edtFileName.text.toString().isNotEmpty()){
                onCreatePreset.invoke(mBinding.edtFileName.text.toString())
            }
        }
    }

}
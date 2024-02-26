package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.fozechmoblive.fluidwallpaper.livefluid.R


class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_modal_bottom_sheet, container, false)
    }

}
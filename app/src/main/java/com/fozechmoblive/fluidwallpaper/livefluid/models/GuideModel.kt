package com.fozechmoblive.fluidwallpaper.livefluid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuideModel(
    val img: Int = 0,
    val title: Int = -1,
    val subText: Int = -1,
) : Parcelable
package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageModel(
    var languageName: String,
    var isoLanguage: String,
    var isCheck: Boolean,
    var image: Int? = null
) : Parcelable {
    constructor() : this("", "", false, 0)
}
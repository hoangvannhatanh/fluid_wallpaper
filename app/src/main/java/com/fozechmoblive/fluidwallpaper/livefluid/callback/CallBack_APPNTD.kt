package com.fozechmoblive.fluidwallpaper.livefluid.callback

import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel

class CallBack {
    interface CallBackLanguage {
        fun callBackLanguage(language: String, position: Int)
    }
    interface CallBackTheme {
        fun callBackTheme(presetModel: PresetModel)
    }
}
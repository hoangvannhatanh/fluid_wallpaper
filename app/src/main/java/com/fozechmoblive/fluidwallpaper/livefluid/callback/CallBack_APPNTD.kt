package com.fozechmoblive.fluidwallpaper.livefluid.callback

class CallBack {
    interface CallBackLanguage {
        fun callBackLanguage(language: String, position: Int)
    }
    interface CallBackLanguageTheme {
        fun callBackLanguageTheme(language: String, position: Int)
    }
}
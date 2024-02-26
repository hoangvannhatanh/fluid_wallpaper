package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.splash

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants.KEY_FIRST_ON_BOARDING
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivitySplashBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.getPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setPref
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.get
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var languageDefaultDevice = "English"
    private var selectLanguage = false
    private var selectOnBoarding = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!getPref(this@SplashActivity, AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE, false)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    languageDefaultDevice = Resources.getSystem().configuration.locales.get(0).toString()
                    if (languageDefaultDevice.lowercase().contains("in_")) {
                        languageDefaultDevice = getString(R.string.indonesian)
                    } else if (languageDefaultDevice.lowercase().contains("en_")) {
                        languageDefaultDevice = getString(R.string.english)
                    } else if (languageDefaultDevice.lowercase().contains("es_")) {
                        languageDefaultDevice = getString(R.string.spanish)
                    } else if (languageDefaultDevice.lowercase().contains("fr_")) {
                        languageDefaultDevice = getString(R.string.french)
                    } else if (languageDefaultDevice.lowercase().contains("pt_")) {
                        languageDefaultDevice = getString(R.string.portuguese)
                    } else if (languageDefaultDevice.lowercase().contains("hi_")) {
                        languageDefaultDevice = getString(R.string.hindi)
                    } else if (languageDefaultDevice.lowercase().contains("de_")) {
                        languageDefaultDevice = getString(R.string.german)
                    } else languageDefaultDevice = getString(R.string.english)

                    setPref(this@SplashActivity, AppConstants.PREFERENCE_SELECTED_LANGUAGE, languageDefaultDevice)
                } else {
                    setPref(this@SplashActivity, AppConstants.PREFERENCE_SELECTED_LANGUAGE, "English")
                }
            } catch (_: Exception) {
                setPref(this@SplashActivity, AppConstants.PREFERENCE_SELECTED_LANGUAGE, "English")
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutActivity() = R.layout.activity_splash

    override fun initViews() {
        super.initViews()
        selectOnBoarding = prefs[KEY_FIRST_ON_BOARDING, false] == true

        Handler(Looper.getMainLooper()).postDelayed({
            moveActivity()
        }, 2000)
    }

    private fun moveActivity() {
        Routes.startLanguageActivity(this, null)
        finish()
    }
}
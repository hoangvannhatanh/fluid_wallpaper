package com.fozechmoblive.fluidwallpaper.livefluid.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.getPref
import java.util.Locale

object LocaleHelper {
    fun setLocale(c: Context): Context {
        return updateResources(c, getLanguageCode(c))
    }

    private fun getLanguageCode(c: Context): String {
        var languageCode = "en"
        if (getPref(
                c,
                AppConstants.PREFERENCE_SELECTED_LANGUAGE,
                c.getString(R.string.english)
            ).toString() != ""
        ) {
            when (getPref(
                c,
                AppConstants.PREFERENCE_SELECTED_LANGUAGE,
                c.getString(R.string.english)
            ).toString()) {
                c.getString(R.string.french) -> {
                    languageCode = "fr"
                }

                c.getString(R.string.german) -> {
                    languageCode = "de"
                }

                c.getString(R.string.hindi) -> {
                    languageCode = "hi"
                }

                c.getString(R.string.indonesian) -> {
                    languageCode = "id"
                }

                c.getString(R.string.portuguese) -> {
                    languageCode = "pt"
                }

                c.getString(R.string.spanish) -> {
                    languageCode = "es"
                }

            }
        }
        return languageCode
    }


    private fun updateResources(contextMain: Context, language: String): Context {
        var context: Context = contextMain
        val locale: Locale = if (language == "zh-rTW") {
            Locale("zh", "TW")
        } else {
            Locale(language)
        }
        Locale.setDefault(locale)
        val res: Resources = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context = context.createConfigurationContext(config)
        return context
    }

}
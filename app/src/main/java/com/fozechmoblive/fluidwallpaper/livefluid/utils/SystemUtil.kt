package com.fozechmoblive.fluidwallpaper.livefluid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

object SystemUtil {
    private var myLocale: Locale? = null

    // Load lại ngôn ngữ đã lưu và thay đổi chúng
    fun setLocale(context: Context) {

        val language = getPreLanguage(context)
        if (language == "") {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    // method phục vụ cho việc thay đổi ngôn ngữ.
    private fun changeLang(lang: String?, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        myLocale = Locale(lang)
        saveLocale(context, lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun saveLocale(context: Context, lang: String?) {
        setPreLanguage(context, lang)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun getPreLanguage(mContext: Context): String? {
        val preferences = mContext.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        Locale.getDefault().displayLanguage
        val lang: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0].language
        } else {
            Resources.getSystem().configuration.locale.language
        }
        return if (!languageApp.contains(lang)) {
            preferences.getString("KEY_LANGUAGE", "en")
        } else {
            preferences.getString("KEY_LANGUAGE", lang)
        }
    }

    private fun setPreLanguage(context: Context, language: String?) {
        if (language == null || language == "") {
            return
        } else {
            val preferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            preferences.edit().putString("KEY_LANGUAGE", language).apply()
        }
    }

    val languageApp: List<String>
        get() {
            val languages: MutableList<String> = ArrayList()
            languages.add("ar"); // Arabic
            languages.add("cs") // Czech
            languages.add("de") // Germany
            languages.add("en") // English
            languages.add("es") // Spanish
            languages.add("fil") // Filipino
            languages.add("fr") // French
            languages.add("hi") // Hindi
            languages.add("hr") // Croatian
            languages.add("in") // indonesian
            languages.add("it") // italian
            languages.add("ko") // korean
            languages.add("ja") //japanese
            languages.add("ms") // Malay
            languages.add("nl") // Dutch
            languages.add("pl") // Polish
            languages.add("pt") // Portugal
            languages.add("ru") // Russian
            languages.add("sr") // Serbian
            languages.add("sv") // Swedish
            languages.add("tr") // Turkish
            languages.add("vi") // Vietnamese
            languages.add("zh") // Chinese
            return languages
        }
}
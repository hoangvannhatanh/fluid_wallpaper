package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.callback.CallBack
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityLanguageBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.getPref
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.onClick
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setPref
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    private var listLanguage: MutableList<Language> = ArrayList()
    private lateinit var stringLanguage: String
    private val languagesAdapter by lazy { LanguageAdapter() }
    private var languageDefaultDevice = ""

    override fun getLayoutActivity() = R.layout.activity_language

    override fun initViews() {
        super.initViews()
        listLanguage.apply {
            add(Language(R.drawable.iv_english, getString(R.string.english), "en"))
            add(Language(R.drawable.iv_hindi, getString(R.string.hindi), "hi"))
            add(Language(R.drawable.iv_spanish, getString(R.string.spanish), "es"))
            add(Language(R.drawable.iv_french, getString(R.string.french), "fr"))
            add(Language(R.drawable.iv_portuguese, getString(R.string.portuguese), "pt"))
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                languageDefaultDevice =
                    Resources.getSystem().configuration.locales.get(0).toString()
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
                } else {
                    languageDefaultDevice = getString(R.string.english)
                    setPref(
                        this@LanguageActivity,
                        AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE,
                        true
                    )
                }
            } else {
                languageDefaultDevice = getString(R.string.english)
                setPref(
                    this@LanguageActivity,
                    AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE,
                    true
                )
            }
        } catch (_: Exception) {
            languageDefaultDevice = getString(R.string.english)
            setPref(
                this@LanguageActivity,
                AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE,
                true
            )
        }

        //set item first for name language app
        for (i in listLanguage.indices) {
            if (listLanguage[i].name == languageDefaultDevice) {
                listLanguage.add(0, listLanguage[i])
                listLanguage.removeAt(i + 1)
            }
        }

        stringLanguage = getPref(
            this@LanguageActivity,
            AppConstants.PREFERENCE_SELECTED_LANGUAGE,
            getString(R.string.english)
        ).toString()

        if (stringLanguage == languageDefaultDevice) {
            setPref(
                this@LanguageActivity,
                AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE,
                false
            )
        } else {
            for (i in listLanguage.indices) {
                if (listLanguage[i].name == stringLanguage) {
                    listLanguage.add(1, listLanguage[i])
                    listLanguage.removeAt(i + 1)
                }
            }
        }

        initRecyclerview()
    }

    override fun onClickViews() {
        super.onClickViews()
        mBinding.ivTick.onClick(1000) {
            setPref(
                this@LanguageActivity,
                AppConstants.PREFERENCE_SELECTED_LANGUAGE,
                stringLanguage
            )
            setPref(
                this@LanguageActivity,
                AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE,
                true
            )

            startActivity(
                Intent(
                    this@LanguageActivity,
                    OnBoardingActivity::class.java
                )
            )
            finish()
        }

        languagesAdapter.callBackLanguage(object : CallBack.CallBackLanguage {
            override fun callBackLanguage(language: String, position: Int) {
                stringLanguage = language
                languagesAdapter.checkSelectView(position)
            }
        })
    }

    private fun initRecyclerview() {
        try {
            mBinding.recyclerView.apply {
                layoutManager = LinearLayoutManager(
                    this@LanguageActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                if (!getPref(
                        this@LanguageActivity,
                        AppConstants.LANGUAGE_FIRST_DEFAULT_LOCALE,
                        false
                    )
                ) {
                    languagesAdapter.addAll(listLanguage, 0)
                } else {
                    languagesAdapter.addAll(listLanguage, 1)
                }
                adapter = languagesAdapter
            }
        } catch (_: Exception) {
        }
    }

    data class Language(val img: Int, val name: String, val key: String)
}
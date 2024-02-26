package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language

import android.content.res.Configuration
import com.ads.control.ads.ITGAd
import com.facebook.shimmer.ShimmerFrameLayout
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager
import com.fozechmoblive.fluidwallpaper.livefluid.ads.AdsManager.nativeAdLanguage
import com.fozechmoblive.fluidwallpaper.livefluid.ads.PreLoadNativeListener
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.app.GlobalApp
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ActivityLanguageBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseActivity
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.goneView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.showToastById
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.visibleView
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.get
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences.set
import com.fozechmoblive.fluidwallpaper.livefluid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>(), PreLoadNativeListener {
    private var adapter: LanguageAdapter? = null
    private var model: LanguageModel? = null

    private var populateNativeAdView = false
    private var shimmerSmall: ShimmerFrameLayout? = null

    override fun getLayoutActivity() = R.layout.activity_language

    override fun initViews() {
        super.initViews()
        shimmerSmall = findViewById(R.id.shimmer_native_large)

        adapter = LanguageAdapter(this, onClickItemLanguage = {
            adapter?.setSelectLanguage(it)
            model = it
        })
        mBinding.rclLanguage.adapter = adapter

        setLanguageDefault()

        AdsManager.setPreLoadNativeCallback(this)
        showNativeLanguage()

    }

    override fun onClickViews() {
        super.onClickViews()
        mBinding.ivDone.setOnClickListener {
            if (model?.languageName != "") {

                prefs[AppConstants.KEY_LANGUAGE] = model?.isoLanguage
                setLocale()
                Routes.startOnBoardingActivity(this)

                finishAffinity()
            } else {
                showToastById(R.string.please_select_language)
            }
        }
    }


    private fun setLocale() {
        val language = prefs.getString(AppConstants.KEY_LANGUAGE, "en")

        if (language == "") {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        } else {
            if (language.equals("", ignoreCase = true)) return
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

    }

    private fun setLanguageDefault() {
        val listLanguages: ArrayList<LanguageModel> = ArrayList()

        val key: String? = prefs[AppConstants.KEY_LANGUAGE, "en"]

        listLanguages.add(LanguageModel("English", "en", false, R.drawable.ic_english))
        listLanguages.add(LanguageModel("Hindi", "hi", false, R.drawable.ic_hindi))
        listLanguages.add(LanguageModel("Spanish", "es", false, R.drawable.ic_spanish))
        listLanguages.add(LanguageModel("French", "fr", false, R.drawable.ic_france))
        listLanguages.add(LanguageModel("Portuguese", "pt", false, R.drawable.ic_portugal))
        listLanguages.add(LanguageModel("Korean", "ko", false, R.drawable.ic_korean))

        listLanguages.add(LanguageModel("Arabic", "ar", false, R.drawable.ic_arabic))
        listLanguages.add(LanguageModel("Croatian", "hr", false, R.drawable.ic_croatia))
        listLanguages.add(LanguageModel("Czech", "cs", false, R.drawable.ic_czech_republic))
        listLanguages.add(LanguageModel("Dutch", "nl", false, R.drawable.ic_dutch))
        listLanguages.add(LanguageModel("Filipino", "fil", false, R.drawable.ic_filipino))
        listLanguages.add(LanguageModel("German", "de", false, R.drawable.ic_german))
        listLanguages.add(LanguageModel("Indonesian", "in", false, R.drawable.ic_indonesian))
        listLanguages.add(LanguageModel("Italian", "it", false, R.drawable.ic_italian))
        listLanguages.add(LanguageModel("Japanese", "ja", false, R.drawable.ic_japanese))
        listLanguages.add(LanguageModel("Malay", "ms", false, R.drawable.ic_malay))
        listLanguages.add(LanguageModel("Polish", "pl", false, R.drawable.ic_polish))
        listLanguages.add(LanguageModel("Russian", "ru", false, R.drawable.ic_russian))
        listLanguages.add(LanguageModel("Serbian", "sr", false, R.drawable.ic_serbian))
        listLanguages.add(LanguageModel("Swedish", "sv", false, R.drawable.ic_swedish))
        listLanguages.add(LanguageModel("Turkish", "tr", false, R.drawable.ic_turkish))
        listLanguages.add(LanguageModel("Vietnamese", "vi", false, R.drawable.ic_vietnamese))
        listLanguages.add(LanguageModel("China", "zh", false, R.drawable.ic_china))


        if (GlobalApp.instance.getLanguage() != null && !listLanguages.contains(GlobalApp.instance.getLanguage())) {
            listLanguages.remove(listLanguages[listLanguages.size - 1])
            val modelLanguage = GlobalApp.instance.getLanguage()
            if (modelLanguage != null) {
                listLanguages.add(modelLanguage)
            }

        }

        for (i in listLanguages.indices) {
            if (key == listLanguages[i].isoLanguage) {
                val data = listLanguages[i]
                data.isCheck = true
                listLanguages.remove(listLanguages[i])
                listLanguages.add(data)
                model = data
                break
            }
        }

        adapter?.submitData(listLanguages)
        mBinding.rclLanguage.smoothScrollToPosition(listLanguages.size - 1)
    }

    private fun showNativeLanguage() {
        if (nativeAdLanguage != null && !populateNativeAdView) {
            Timber.e("initAdmob: $nativeAdLanguage")
            mBinding.frAds.visibleView()
            populateNativeAdView = true
            ITGAd.getInstance().populateNativeAdView(
                this, nativeAdLanguage, mBinding.frAds, shimmerSmall
            )
        } else {
            mBinding.frAds.goneView()
            Timber.d(
                "LanguageActivity initAds nativeAdViewLanguage = $nativeAdLanguage - nativeAdLanguage = $nativeAdLanguage"
            )
        }
    }

    override fun onLoadNativeSuccess() {

        showNativeLanguage()
    }

    override fun onLoadNativeFail() {
        if (nativeAdLanguage != null) {
            mBinding.frAds.visibleView()
        } else {
            mBinding.frAds.goneView()
        }
    }
}
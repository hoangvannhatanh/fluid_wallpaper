#### Template android project by Infinity
#### How to use
### Screen
Name Convention for Activity or Fragment  `Name`Activity and `Name`Fragment
- SplashActivity
- LanguageActivity
- OnBoardingActivity
- PermissionActivity (`option depend on Project`)
- MainActivity

## Flow Advert

- ### SpashScreen
`Check state of KEY_SELECT_LANGUAGE & KEY_FIRST_ON_BOARDING`
```
 selectLanguage = prefs[KEY_SELECT_LANGUAGE, false] == true
 selectOnBoarding = prefs[KEY_FIRST_ON_BOARDING, false] == true
```
Need to check for load Ad
```
        if (!selectLanguage) {
            loadNativeLanguage(this@SplashActivity)
        }
        if (!selectOnBoarding) {
            loadNativeOnBoarding(this)
        }
```
After need to check have `Permission`
```
        if (ContextCompat.checkSelfPermission(this@SplashActivity,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
        } else {
            AdsConfig.loadNativePermission(this)
        }
```
Next Screen will show `LanguageActivity`, `OnBoardingActivity` or `PermissionActivity`

- ### LanguageActivity, OnBoardingActivity, PermissionActivity (All of Screen have NativeAd)
Must to check have native and show in `initViews()` && PreLoadNativeListener (`onLoadNativeSuccess`)
```
        AdsConfig.setPreLoadNativeCallback(this)
        showNativeLanguage()
```
Callback from PreloadnativeListener
```
        override fun onLoadNativeSuccess() {
                showNativeLanguage()
            }

        override fun onLoadNativeFail() {
                if (nativeAdLanguage!=null) {
                    mBinding.frAds.visibleView()
                }else{
                    mBinding.frAds.goneView()
                }
            }
```

- ### Add CheckList
Change id in `id_ads.xml`
```
<resources>
    <string name="adjust_token" translatable="false"></string>
    <string name="facebook_app_id" translatable="false"></string>
    <string name="facebook_client_token" translatable="false"></string>
</resources>
```
- ### Tracking Event
Use object `ITGTrackingHelper`
function `addScreenTrack` added for whole base project user don't need add for Activity
function `logEvent` need add for primary button in screen
```
    ITGTrackingHelper.logEvent(AnalyticsHelper.ClickEditButton, Bundle?)
```
function `fromScreenToScreen` for tracking Screen (added for whole base project user don't need add for Activity )
```
private fun addTrackingMoveScreen(fromContext: Activity, intent: Intent) {
        intent.component?.className?.substringAfterLast(".")?.let {
            ITGTrackingHelper.fromScreenToScreen(
                fromContext::class.java.simpleName, it
            )
        }
    }
```



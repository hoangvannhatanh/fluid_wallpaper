package com.fozechmoblive.fluidwallpaper.livefluid.utils

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

/**
 * Required implementation
 * implementation 'com.google.firebase:firebase-analytics-ktx'
 */
object ITGTrackingHelper {


    private var mFirebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    /**
     * Khi gắn cái này cần trao đổi với PM, PO, BA
     */
    fun userProperty(name: String, value: String): ITGTrackingHelper {
        Firebase.analytics.setUserProperty(name, value)
        return this
    }


    /**
     * Tracking theo sự kiên được lên kịch bản.
     * Gắn theo kịch bản của PO, PM, BA.
     */
    fun logEvent(eventName: String, params: Bundle?): ITGTrackingHelper {
        Firebase.analytics.logEvent(eventName, params)
        return this
    }

    fun logEventClick(activityName: String, eventName: String, params: Bundle?): ITGTrackingHelper {
        Log.d("ITGTrackingHelper", "logEvent: $activityName click $eventName")
        Firebase.analytics.logEvent("${activityName}_click_$eventName", params)
        return this
    }

    /**
     * Chỉ gắn cho tracking theo màn hình theo yêu cầu của PM, PO, BA
     */
    fun addScreenTrack(screenName: String): ITGTrackingHelper {
        Firebase.analytics.logEvent(screenName, null)
        return this
    }


    /**
     * Chỉ gắn cho tracking theo màn hình theo yêu cầu của PM, PO, BA
     */
    fun fromScreenToScreen(fromScreen: String, toScreen: String): ITGTrackingHelper {
        Log.d("ITGTrackingHelper", "fromScreenToScreen: $fromScreen to $toScreen")
        Firebase.analytics.logEvent(
            "${fromScreen}_${toScreen}", null
        )
        return this
    }

    object Params {}
}
package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

fun View.setOnCustomTouchView(onCustomTouchListener: OnCustomTouchListener?) {
    setOnTouchListener(object : View.OnTouchListener {
        private var rect: Rect? = null
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (onCustomTouchListener == null) return false
            if (event.action == MotionEvent.ACTION_DOWN) {
                rect = Rect(v.left, v.top, v.right, v.bottom)
                onCustomTouchListener.onCustomTouchDown(v, event)
            } else if (rect != null && !rect!!.contains(
                    v.left + event.x.toInt(),
                    v.top + event.y.toInt()
                )
            ) {
                onCustomTouchListener.onCustomTouchMoveOut(v, event)
                return false
            } else if (event.action == MotionEvent.ACTION_UP) {
                onCustomTouchListener.onCustomTouchUp(v, event)
            } else {
                onCustomTouchListener.onCustomTouchOther(v, event)
            }
            return true
        }
    })
}

fun View.setOnCustomTouchViewScale(customClickListener: OnCustomClickListener?) {
    setOnCustomTouchView(object : OnCustomTouchListener {
        private fun setScale(scale: Float) {
            scaleX = scale
            scaleY = scale
        }

        override fun onCustomTouchDown(view: View, event: MotionEvent) {
            setScale(0.9f)
        }

        override fun onCustomTouchMoveOut(view: View, event: MotionEvent) {
            setScale(1f)
        }

        override fun onCustomTouchUp(view: View, event: MotionEvent) {
            setScale(1f)
            customClickListener?.onCustomClick(view, event)
        }

        override fun onCustomTouchOther(view: View, event: MotionEvent) {
            setScale(1f)
        }
    })
}

fun View.setOnCustomTouchViewScaleNotOther(customClickListener: OnCustomClickListener?) {
    setOnCustomTouchView(object : OnCustomTouchListener {
        private fun setScale(scale: Float) {
            scaleX = scale
            scaleY = scale
        }

        override fun onCustomTouchDown(view: View, event: MotionEvent) {
            setScale(0.9f)
        }

        override fun onCustomTouchMoveOut(view: View, event: MotionEvent) {
            setScale(1f)
        }

        override fun onCustomTouchUp(view: View, event: MotionEvent) {
            setScale(1f)
            customClickListener?.onCustomClick(view, event)
        }

        override fun onCustomTouchOther(view: View, event: MotionEvent) {

        }
    })
}

fun View.setOnCustomTouchViewAlphaNotOther(customClickListener: OnCustomClickListener?) {
    setOnCustomTouchView(object : OnCustomTouchListener {
        var isTouchDown = false
        var isTouchMoveOutAndUp = false
        private fun setAlpha(ap: Float) {
            alpha = ap
        }

        override fun onCustomTouchDown(view: View, event: MotionEvent) {
            isTouchDown = true
            setAlpha(0.7f)
        }

        override fun onCustomTouchMoveOut(view: View, event: MotionEvent) {
            isTouchMoveOutAndUp = true
            setAlpha(1f)
        }

        override fun onCustomTouchUp(view: View, event: MotionEvent) {
            isTouchMoveOutAndUp = true
            setAlpha(1f)
            customClickListener?.onCustomClick(view, event)
        }

        override fun onCustomTouchOther(view: View, event: MotionEvent) {
            if (!isTouchMoveOutAndUp && isTouchDown) setAlpha(1f)
            isTouchDown = false
            isTouchMoveOutAndUp = false
        }
    })
}

interface OnCustomTouchListener {
    fun onCustomTouchDown(view: View, event: MotionEvent)
    fun onCustomTouchMoveOut(view: View, event: MotionEvent)
    fun onCustomTouchUp(view: View, event: MotionEvent)
    fun onCustomTouchOther(view: View, event: MotionEvent)
}

interface OnCustomClickListener {
    fun onCustomClick(view: View, event: MotionEvent)
}
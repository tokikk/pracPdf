package net.tkkk42.pracpdf

import android.content.Context
import android.opengl.Matrix
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ViewParent
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.motion.widget.MotionInterpolator
import java.util.jar.Attributes

class tkImageView: AppCompatImageView {
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    private var scaleFactor = 1.0f

    init {
        scaleGestureDetector = ScaleGestureDetector(context, simpleOnScaleGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.actionMasked

        if (event?.pointerCount == 2) {
            when (action) {
                MotionEvent.ACTION_MOVE -> parent.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    private inner class simpleOnScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            //scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 5.0f))
            this@tkImageView.scaleX = scaleFactor
            this@tkImageView.scaleY = scaleFactor

            return true
        }
    }

    private fun resetScale() {
        this@tkImageView.scaleX = 1.0f
        this@tkImageView.scaleY = 1.0f
    }


    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}
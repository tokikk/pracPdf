package net.tkkk42.pracpdf

import android.content.Context
import android.opengl.Matrix
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.motion.widget.MotionInterpolator
import androidx.core.view.GestureDetectorCompat
import java.util.jar.Attributes

class tkImageView: AppCompatImageView {
    private lateinit var gestureDetectorCompat : GestureDetectorCompat
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    private var ktranslationX = 0f
    private var ktranslationY = 0f
    private var scaleFactor = 1f
    private var imageWidth = 0f
    private var imageHeight = 0f
    private var defaultImageWidth = 0f
    private var defaultImageHeight = 0f
    private var viewPortWidth = 0f
    private var viewPortHeight = 0f

    init {
        gestureDetectorCompat = GestureDetectorCompat(context, gestureListner())
        scaleGestureDetector = ScaleGestureDetector(context, simpleOnScaleGestureListener())

        val viewTreeObserver = this.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    this@tkImageView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val imageAspectRatio = this@tkImageView.drawable.intrinsicHeight.toFloat() / this@tkImageView.drawable.intrinsicWidth.toFloat()
                    val viewAspectRatio = this@tkImageView.height.toFloat() / this@tkImageView.width.toFloat()

                    defaultImageWidth = if (imageAspectRatio < viewAspectRatio) {
                        // landscape image
                        this@tkImageView.width.toFloat()
                    } else {
                        // Portrait image
                        this@tkImageView.height.toFloat() / imageAspectRatio
                    }

                    defaultImageHeight = if (imageAspectRatio < viewAspectRatio) {
                        // landscape image
                        this@tkImageView.width.toFloat() * imageAspectRatio
                    } else {
                        // Portrait image
                        this@tkImageView.height.toFloat()
                    }

                    imageWidth = defaultImageWidth
                    imageHeight = defaultImageHeight

                    viewPortWidth = this@tkImageView.width.toFloat()
                    viewPortHeight = this@tkImageView.height.toFloat()
                }
            })
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.actionMasked

        if (event?.pointerCount == 2) {
            when (action) {
                MotionEvent.ACTION_MOVE -> parent.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        gestureDetectorCompat.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    private inner class gestureListner : GestureDetector.SimpleOnGestureListener( ) {
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            val translationX = ktranslationX - distanceX
            val translationY = ktranslationY - distanceY

            this@tkImageView.translationX = translationX
            this@tkImageView.translationY = translationY

            return true
        }
    }

    private inner class simpleOnScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 5.0f))
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
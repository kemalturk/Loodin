package com.turkapp.kmltrk.library

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.TypedValue

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by kmltrk on 11/29/2017.
 */
abstract class Indicator(private val context: Context): Drawable() {

  var mPaint = Paint()
  var centerW = 0f
  var centerH = 0f
  private var invalidateListener: InvalidateListener? = null

  private var mAnimators: ArrayList<ValueAnimator>? = null
  private var mUpdateListeners: HashMap<ValueAnimator, ValueAnimator.AnimatorUpdateListener> = HashMap()

  init {
    mPaint.color = Color.WHITE
    mPaint.style = Paint.Style.FILL
    mPaint.isAntiAlias = true
  }

  abstract fun draw(canvas: Canvas?, paint: Paint)

  override fun draw(canvas: Canvas?) {
    draw(canvas,mPaint)
  }

  override fun setAlpha(p0: Int) {
  }

  override fun getOpacity(): Int {
    return PixelFormat.OPAQUE
  }

  override fun setColorFilter(p0: ColorFilter?) {
  }

  fun convertDpToPx(dp: Float): Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, context.resources.displayMetrics)
  }

  //////////////////////
  //Animatable
  ///////////////////////

  abstract fun onCreateAnimators(): ArrayList<ValueAnimator>

  fun start(listener: InvalidateListener) {

    invalidateListener = listener

    ensureAnimators()

    if (isStarted()) return

    startAnimators()
    invalidateSelf()

  }

  fun stop() {
    stopAnimators()
  }

  private fun ensureAnimators(){

    if (mAnimators == null || mAnimators!!.size == 0){
      mAnimators = onCreateAnimators()
    }

  }

  private fun startAnimators(){

    mAnimators?.forEach{ animator ->

      val updateListener = mUpdateListeners[animator]

      if (updateListener != null){
        animator.addUpdateListener(updateListener)
      }

      animator.start()

    }

  }

  private fun stopAnimators(){

    if (mAnimators != null){
      mAnimators?.forEach { animator ->
        if (animator.isStarted){
          animator.removeAllUpdateListeners()
          animator.end()
        }
      }
    }

  }

  fun addUpdateListener(animator: ValueAnimator, listener: ValueAnimator.AnimatorUpdateListener){
    mUpdateListeners.put(animator, listener)
  }

  fun postInvalidate(){
    invalidateListener?.onInvalidate()
  }

  fun isStarted(): Boolean{

    mAnimators?.forEach { animator ->
      return animator.isRunning
    }

    return false
  }

}
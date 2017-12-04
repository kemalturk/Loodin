package com.turkapp.kmltrk.library

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.TypedValue

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by kmltrk on 11/29/2017.
 */

abstract class Indicator(private val context: Context, parentW: Int, parentH: Int,
                         color: Int, private val invalidateListener: InvalidateListener?): Drawable(), Animatable {

  var mPaint = Paint()
  var centerW = 0f
  var centerH = 0f
  var minSide = 1f //This is for resize the indicator according to short edge.

  private var mAnimators: ArrayList<ValueAnimator>? = null
  private var mUpdateListeners: HashMap<ValueAnimator, ValueAnimator.AnimatorUpdateListener> = HashMap()

  init {
    mPaint.color = Color.WHITE
    mPaint.style = Paint.Style.FILL
    mPaint.isAntiAlias = true

    if (color != 0 ) mPaint.color = color

    centerH = parentH/2f
    centerW = parentW/2f

    minSide = when {
      parentH > parentW -> parentW.toFloat()
      parentW > parentH -> parentH.toFloat()
      else -> parentW.toFloat()
    }

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


  override fun start() {

    ensureAnimators()

    if (isStarted() || mAnimators == null) return

    startAnimators()
    postInvalidate()

  }

  override fun stop() {
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
          animator.removeAllListeners()
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

  override fun isRunning(): Boolean {

    mAnimators?.forEach { animator ->
      return animator.isRunning
    }

    return false
  }

  private fun isStarted(): Boolean{

    mAnimators?.forEach { animator ->
      return animator.isStarted
    }

    return false
  }

}
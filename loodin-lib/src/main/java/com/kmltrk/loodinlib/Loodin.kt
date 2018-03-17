package com.kmltrk.loodinlib

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.kmltrk.loodinlib.Views.BallBarrierJumpingView
import com.kmltrk.loodinlib.Views.BallPulseIndicatorView
import com.kmltrk.loodinlib.Views.PlusCircleIndicatorView
import com.kmltrk.loodinlib.Views.WindTurbineIndicatorView

/**
 * Created by kmltrk on 11/29/2017.
 */

class Loodin: View, InvalidateListener {

  private var color = 0

  private var w = 0
  private var h = 0
  private var ind = 0
  private var indicator: Indicator? = null

  private var mShouldStartAnimationDrawable = false

  constructor(context: Context?) : super(context){
    init(context, null, 0, 0)
  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
    init(context, attrs, 0, 0)
  }

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
    init(context, attrs, defStyleAttr, 0)
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int,
              defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){
    init(context, attrs, defStyleAttr, defStyleRes)
  }

  private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int){

    val a = context!!.obtainStyledAttributes(attrs, R.styleable.Loodin, defStyleAttr, defStyleRes)

    try {

      color = a.getColor(R.styleable.Loodin_paintColor, Color.WHITE)
      ind = a.getInt(R.styleable.Loodin_indicator, 0)
      a.recycle()

    }catch (e: Exception){
      Log.e("Err", e.message)
    }

  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    w = MeasureSpec.getSize(widthMeasureSpec)
    h = MeasureSpec.getSize(heightMeasureSpec)
    selectIndicator(ind)

  }

  @Synchronized override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    drawTrack(canvas)
  }

  private fun drawTrack(canvas: Canvas) {
    if (indicator != null) {

      canvas.save()
      indicator?.draw(canvas)
      canvas.restore()

      if (mShouldStartAnimationDrawable) {
        indicator?.start()
        mShouldStartAnimationDrawable = false
      }
    }
  }


  private fun selectIndicator(p0: Int){

    indicator = when(p0){
      0 -> PlusCircleIndicatorView(context, w, h, color, this)
      1 -> BallPulseIndicatorView(context, w, h, color, this)
      2 -> BallBarrierJumpingView(context, w, h, color, this)
      3 -> WindTurbineIndicatorView(context, w, h, color, this)
      else -> PlusCircleIndicatorView(context, w, h, color, this)
    }

  }

  override fun onInvalidate() {
    invalidate()
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    indicator?.start()
  }

  fun startAnim(){
    mShouldStartAnimationDrawable = true
    invalidate()
  }

  fun stopAnim(){
    indicator?.stop()
    mShouldStartAnimationDrawable = false
    invalidate()
  }

}
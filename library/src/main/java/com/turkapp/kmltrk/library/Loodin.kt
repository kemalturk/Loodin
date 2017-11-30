package com.turkapp.kmltrk.library

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.turkapp.kmltrk.library.Views.CircleIndicatorView

/**
 * Created by kmltrk on 11/29/2017.
 */
class Loodin: View {

  var color = 0

  private var w = 0
  private var h = 0
  private var ind = 0
  private var indicator: Indicator? = null

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

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    indicator?.draw(canvas)

    if (!indicator!!.isStarted()) startAnim()

  }

  private fun selectIndicator(p0: Int){

    indicator = when(p0){
      0 -> CircleIndicatorView(context, w, h, color)
      else -> CircleIndicatorView(context, w, h, color)
    }

  }

  fun startAnim(){
    if (indicator != null) indicator?.start(object : InvalidateListener{
      override fun onInvalidate() {
        invalidate()
      }
    })
  }

  fun stopAnim(){
    indicator?.stop()
  }

}
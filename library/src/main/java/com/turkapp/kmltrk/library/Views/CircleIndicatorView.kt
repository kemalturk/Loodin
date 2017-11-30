package com.turkapp.kmltrk.library.Views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.turkapp.kmltrk.library.Indicator

/**
 * Created by kmltrk on 11/29/2017.
 */
class CircleIndicatorView(context: Context, parentW: Int, parentH: Int,
                          color: Int) : Indicator(context) {

  var radius = 1f
  var ud = 1f
  var maxUd = 20f

  init {
    if (color != 0 ) mPaint.color = color

    centerH = parentH/2f
    centerW = parentW/2f

    val wORh = when {
      parentH > parentW -> parentW
      parentW > parentH -> parentH
      else -> parentW
    }

    radius = convertDpToPx((wORh.toFloat() / 6) - maxUd / 3 )

  }

  override fun draw(canvas: Canvas?, paint: Paint) {

    canvas?.drawCircle(centerW, centerH, radius, paint) //Center
    canvas?.drawCircle(centerW + (radius*-(2)) + -ud, centerH, radius, paint) //Left
    canvas?.drawCircle(centerW + (radius*2) + ud, centerH, radius, paint) //Right
    canvas?.drawCircle(centerW, centerH + (radius*-(2)) + -ud, radius, paint) //Up
    canvas?.drawCircle(centerW, centerH + (radius*2) + ud, radius, paint) //Down

  }

  override fun onCreateAnimators(): ArrayList<ValueAnimator> {

    val animators = ArrayList<ValueAnimator>()

    val tAnim = ValueAnimator.ofFloat(1f, maxUd, 1f)
    tAnim.duration = 1500
    tAnim.repeatCount = -1
    tAnim.startDelay = 350
    addUpdateListener(tAnim, ValueAnimator.AnimatorUpdateListener { animation ->
      ud = animation.animatedValue as Float
      postInvalidate()
    })

    animators.add(tAnim)

    return animators

  }




}
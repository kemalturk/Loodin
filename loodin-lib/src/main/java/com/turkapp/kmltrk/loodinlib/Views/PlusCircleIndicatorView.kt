package com.turkapp.kmltrk.loodinlib.Views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.turkapp.kmltrk.loodinlib.Indicator
import com.turkapp.kmltrk.loodinlib.InvalidateListener

/**
 * Created by kmltrk on 11/29/2017.
 */

class PlusCircleIndicatorView(context: Context, parentW: Int, parentH: Int, color: Int, invalidateListener: InvalidateListener?):
    Indicator(context, parentW, parentH, color, invalidateListener) {

  private var radius = 1f
  private var ud = 1f
  private var maxUd = 20f

  init {

    radius = convertDpToPx((minSide / 6) - maxUd / 3 )

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
    tAnim.duration = 1000
    tAnim.repeatCount = -1
    tAnim.startDelay = 350
    addUpdateListener(tAnim, ValueAnimator.AnimatorUpdateListener { animation ->
      ud = animation.animatedValue as Float
      postInvalidate()
    })

    animators.add(tAnim)

    return animators

  }

  override fun onAnimatorsStop() {
  }


}
package com.turkapp.kmltrk.library.Views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.turkapp.kmltrk.library.Indicator
import java.util.ArrayList

/**
 * Created by kmltrk on 12/1/2017.
 */

class BallPulseIndicatorView(context: Context, parentW: Int, parentH: Int,
                             color: Int): Indicator(context) {

  private var path = Path()
  private var radius = 1f
  private var r1 = 1f
  private var r2 = 1f
  private var margin = 5f

  init {
    if (color != 0 ) mPaint.color = color

    centerH = parentH/2f
    centerW = parentW/2f

    val wORh = when {
      parentH > parentW -> parentW
      parentW > parentH -> parentH
      else -> parentW
    }

    radius = convertDpToPx((wORh.toFloat() / 6) - (margin/2))
    r2 = radius
    invalidatePath()

  }

  private fun invalidatePath(){

    path.reset()
    path.addCircle(centerW, centerH, r1, Path.Direction.CW) //Center
    path.addCircle(centerW + margin + (radius * 2) , centerH, r2, Path.Direction.CW) //Right
    path.addCircle(centerW + (-margin + (radius * (-2))), centerH, r2, Path.Direction.CW) //Left

  }

  override fun draw(canvas: Canvas?, paint: Paint) {

    canvas?.drawPath(path, paint)

  }

  override fun onCreateAnimators(): ArrayList<ValueAnimator> {

    val duration = 1000L
    val animators = ArrayList<ValueAnimator>()

    val anim1 = ValueAnimator.ofFloat(1f, radius, 1f)
    anim1.duration = duration
    anim1.repeatCount = -1
    anim1.startDelay = 350
    addUpdateListener(anim1, ValueAnimator.AnimatorUpdateListener { animation ->
      r1 = animation.animatedValue as Float
      invalidatePath()
      postInvalidate()
    })

    val anim2 = ValueAnimator.ofFloat(radius, 1f, radius)
    anim2.duration = duration
    anim2.repeatCount = -1
    anim2.startDelay = 350
    addUpdateListener(anim2, ValueAnimator.AnimatorUpdateListener { animation ->
      r2 = animation.animatedValue as Float
      invalidatePath()
      postInvalidate()
    })

    animators.add(anim1)
    animators.add(anim2)

    return animators
  }


}
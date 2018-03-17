package com.kmltrk.loodinlib.Views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.kmltrk.loodinlib.Indicator
import com.kmltrk.loodinlib.InvalidateListener
import java.util.ArrayList

/**
 * Created by kmltrk on 12/1/2017.
 */

class BallPulseIndicatorView(context: Context, parentW: Int, parentH: Int, color: Int, invalidateListener: InvalidateListener?):
    Indicator(context, parentW, parentH, color, invalidateListener) {

  private var path = Path()
  private var radius = 1f //Max radius
  private var r1 = 1f //Center ball radius
  private var r2 = 1f //Left and Right ball radius
  private var margin = 5f //Left and Right ball margin

  init {

    radius = convertDpToPx((minSide / 6) - (margin/2))
    r2 = radius
    invalidatePath()

  }

  private fun invalidatePath(){

    path.reset()
    path.addCircle(centerW, centerH, r1, Path.Direction.CW) //Center
    path.addCircle(centerW + margin + (radius * 2) , centerH, r2, Path.Direction.CW) //Right
    path.addCircle(centerW + (-margin + (radius * (-2))), centerH, r2, Path.Direction.CW) //Left
    postInvalidate()

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
    })

    val anim2 = ValueAnimator.ofFloat(radius, 1f, radius)
    anim2.duration = duration
    anim2.repeatCount = -1
    anim2.startDelay = 350
    addUpdateListener(anim2, ValueAnimator.AnimatorUpdateListener { animation ->
      r2 = animation.animatedValue as Float
      invalidatePath()
    })

    animators.add(anim1)
    animators.add(anim2)

    return animators
  }

  override fun onAnimatorsStop() {
  }


}
package com.kmltrk.loodinlib.Views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import com.kmltrk.loodinlib.Indicator
import com.kmltrk.loodinlib.InvalidateListener
import java.util.ArrayList


/**
 * Created by kmltr on 12/8/2017.
 */
class WindTurbineIndicatorView(context: Context, parentW: Int, parentH: Int, color: Int, invalidateListener: InvalidateListener?) :
    Indicator(context, parentW, parentH, color, invalidateListener) {

  private var sTa = 1f
  private var sTb = 1f
  private var bTa = 1f
  private var bTb = 1f

  private val matrix = Matrix()
  private val smallTriangle = Path()
  private val bigTriangle = Path()

  private var rotate = 0f

  private var paint2 = Paint(mPaint)


  init {

    sTa = minSide * (1f / 8f)
    sTb = sTa * Math.sqrt(2.0).toFloat()
    bTa = sTb
    bTb = bTa * Math.sqrt(2.0).toFloat()

    mPaint.strokeWidth = 10f
    mPaint.style = Paint.Style.FILL

    paint2.color = color.minus(40)

    smallTriangle.reset()
    smallTriangle.moveTo(centerW, centerH)
    smallTriangle.lineTo(centerW, centerH - sTa)
    smallTriangle.lineTo(centerW + sTa, centerH - sTa)
    smallTriangle.lineTo(centerW, centerH)
    smallTriangle.close()

    bigTriangle.reset()
    bigTriangle.moveTo(centerW, centerH)
    bigTriangle.lineTo(centerW + bTb, centerH)
    bigTriangle.lineTo(centerW + sTa, centerH - sTa)
    bigTriangle.lineTo(centerW, centerH)
    bigTriangle.close()

  }

  override fun draw(canvas: Canvas?, paint: Paint) {

    for (i in 0..3){

      matrix.setRotate((90f * i) + rotate , centerW, centerH)
      smallTriangle.transform(matrix)
      bigTriangle.transform(matrix)
      canvas?.drawPath(smallTriangle, paint2)
      canvas?.drawPath(bigTriangle, paint)

    }

    canvas?.drawLine(centerW, centerH, centerW, centerH + sTa * 3, paint)

  }

  override fun onCreateAnimators(): ArrayList<ValueAnimator> {

    val animators = ArrayList<ValueAnimator>()

    val turnAnim = ValueAnimator.ofFloat(1f, 4f, 1f)
    turnAnim.duration = 10000
    turnAnim.repeatCount = -1
    turnAnim.startDelay = 350
    addUpdateListener(turnAnim, ValueAnimator.AnimatorUpdateListener { animation ->
      rotate = animation.animatedValue as Float
      postInvalidate()
    })

    animators.add(turnAnim)

    return animators

  }

  override fun onAnimatorsStop() {

    rotate = 0f

  }

}
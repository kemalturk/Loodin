package com.turkapp.kmltrk.library.Views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.turkapp.kmltrk.library.Indicator
import com.turkapp.kmltrk.library.InvalidateListener
import java.util.ArrayList

/**
 * Created by kmltrk on 12/1/2017.
 */
class BallBarrierJumpingView(context: Context, private val parentW: Int, parentH: Int, color: Int, invalidateListener: InvalidateListener?):
    Indicator(context, parentW, parentH, color, invalidateListener) {

  private var radius = 1f

  private var maxJumpHeight = 1f
  private var minJumpHeight = 1f
  private var jumpHeight = 0f

  private var littleJumpCounter = 0

  private var rectWidth = 1f

  private var slideCount = 0f

  init {

    radius = convertDpToPx((minSide / 2) / 4)
    maxJumpHeight = (parentH/2) - radius
    minJumpHeight = (maxJumpHeight /10)

    rectWidth = (parentW.toFloat() + radius*2)

    mPaint.strokeWidth = 5f

  }

  override fun draw(canvas: Canvas?, paint: Paint) {

    canvas?.drawLine(0f, centerH + radius, parentW.toFloat(), centerH + radius, paint)

    canvas?.drawCircle(centerW, centerH - jumpHeight, radius, paint)

    canvas?.drawRect((parentW.toFloat()) - slideCount, centerH - radius,
        rectWidth - slideCount, centerH + radius, paint)


  }

  override fun onCreateAnimators(): ArrayList<ValueAnimator> {

    littleJumpCounter = 0

    val animators = ArrayList<ValueAnimator>()

    val littleJumpAnim = ValueAnimator.ofFloat(0f, minJumpHeight, 0f)
    littleJumpAnim.repeatCount = 0
    littleJumpAnim.duration = 500L
    littleJumpAnim.startDelay = 0
    addUpdateListener(littleJumpAnim, ValueAnimator.AnimatorUpdateListener { animation ->

      jumpHeight = animation.animatedValue as Float
      postInvalidate()

    })

    val bigJumpAnim = ValueAnimator.ofFloat(0f, maxJumpHeight, 0f)
    bigJumpAnim.repeatCount = 0
    bigJumpAnim.duration = 1000L
    bigJumpAnim.startDelay = 0
    bigJumpAnim.addUpdateListener{ animation ->

      jumpHeight = animation.animatedValue as Float
      postInvalidate()

    }

    val slideAnim = ValueAnimator.ofFloat(0f, parentW.toFloat() + rectWidth)
    slideAnim.repeatCount = 0
    slideAnim.duration = 1500L
    slideAnim.startDelay = 0
    slideAnim.addUpdateListener { animation ->

      slideCount = animation.animatedValue as Float
      postInvalidate()

    }


    littleJumpAnim.addListener(object : AnimatorListenerAdapter(){
      override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationEnd(animation)
        println("finish")
        littleJumpCounter++
        if (littleJumpCounter >= 5){
          bigJumpAnim.start()
          littleJumpCounter = 0
        }else{
          littleJumpAnim.start()
        }
      }
    })


    bigJumpAnim.addListener(object : AnimatorListenerAdapter(){
      override fun onAnimationStart(animation: Animator?) {
        super.onAnimationStart(animation)
        slideAnim.start()
      }
      override fun onAnimationEnd(p0: Animator?) {
        littleJumpAnim.start()
      }
    })

    animators.add(slideAnim)
    animators.add(bigJumpAnim)
    animators.add(littleJumpAnim)

    return animators

  }
}
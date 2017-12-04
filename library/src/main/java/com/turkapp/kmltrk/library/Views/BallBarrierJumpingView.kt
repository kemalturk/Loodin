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

  private var groundSize = 0
  private var groundSpace = 1f
  private var groundSlideCount = 0f

  init {

    mPaint.strokeWidth = 5f

    //Ball
    radius = convertDpToPx((minSide / 2) / 4)
    maxJumpHeight = (parentH/2) - radius
    minJumpHeight = (maxJumpHeight /10)

    //Barrier
    rectWidth = (parentW.toFloat() + radius*2)

    //Ground
    groundSize = (parentW / radius).toInt() + 1
    groundSpace = (parentW / groundSize).toFloat()

  }

  override fun draw(canvas: Canvas?, paint: Paint) {

    canvas?.drawLine(0f, centerH + radius, parentW.toFloat(), centerH + radius, paint)

    for ( i in 0..groundSize){
      canvas?.drawLine((groundSpace * i) - groundSlideCount, centerH + radius,
          ((groundSpace * i) - groundSpace)- groundSlideCount, (centerH + radius) + radius / 2, paint)
    }

    canvas?.drawCircle(centerW, centerH - jumpHeight, radius, paint)

    canvas?.drawRect((parentW.toFloat()) - slideCount, centerH - radius,
        rectWidth - slideCount, centerH + radius, paint)


  }

  override fun onCreateAnimators(): ArrayList<ValueAnimator> {

    val animators = ArrayList<ValueAnimator>()

    val groundSlideAnim = ValueAnimator.ofFloat(0f, groundSpace)
    groundSlideAnim.repeatCount = -1
    groundSlideAnim.duration = (1500L / groundSize + 4)
    groundSlideAnim.startDelay = 0
    addUpdateListener(groundSlideAnim, ValueAnimator.AnimatorUpdateListener { animation ->

      groundSlideCount = animation.animatedValue as Float
      postInvalidate()

    })

    val littleJumpAnim = ValueAnimator.ofFloat(0f, minJumpHeight, 0f)
    littleJumpAnim.repeatCount = 0
    littleJumpAnim.duration = 500L
    littleJumpAnim.startDelay = 0
    littleJumpAnim.addUpdateListener { animation ->
      jumpHeight = animation.animatedValue as Float
      postInvalidate()
    }

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

    bigJumpAnim.addListener(object : AnimatorListenerAdapter(){
      override fun onAnimationStart(animation: Animator?) {
        super.onAnimationStart(animation)
        slideAnim.start()
      }
      override fun onAnimationEnd(p0: Animator?) {
        littleJumpAnim.start()
      }
    })

    littleJumpAnim.addListener(object : AnimatorListenerAdapter(){
      override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationEnd(animation)

        littleJumpCounter++
        if (littleJumpCounter >= 5){
          bigJumpAnim.start()
          littleJumpCounter = 0
        }else{
          littleJumpAnim.start()
        }

      }
    })

    groundSlideAnim.addListener(object : AnimatorListenerAdapter(){

      override fun onAnimationStart(animation: Animator?) {
        super.onAnimationStart(animation)
        littleJumpCounter = 0
        littleJumpAnim.start()
      }

    })

    animators.add(groundSlideAnim)

    return animators

  }
}
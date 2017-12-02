package com.turkapp.kmltrk.library.Views

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
class BallBarrierJumpingView(context: Context, parentW: Int, parentH: Int, color: Int, invalidateListener: InvalidateListener?):
    Indicator(context, parentW, parentH, color, invalidateListener) {

  override fun draw(canvas: Canvas?, paint: Paint) {
  }

  override fun onCreateAnimators(): ArrayList<ValueAnimator> {

    val animators = ArrayList<ValueAnimator>()

    return animators

  }
}
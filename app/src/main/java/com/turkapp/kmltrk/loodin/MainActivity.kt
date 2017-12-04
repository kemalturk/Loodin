package com.turkapp.kmltrk.loodin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    btnStart.setOnClickListener {
      circleIndicator?.startAnim()
    }

    btnStop.setOnClickListener {
      circleIndicator?.stopAnim()
    }

    btnAll.setOnClickListener {
      startActivity(Intent(this@MainActivity, AllActivity :: class.java))
    }

  }
}

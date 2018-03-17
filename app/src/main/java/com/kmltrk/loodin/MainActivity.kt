package com.kmltrk.loodin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.kmltrk.loodinlib.Loodin

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val btnStart: Button = findViewById(R.id.btnStart)
    val btnStop: Button = findViewById(R.id.btnStop)
    val btnAll: Button = findViewById(R.id.btnAll)
    val indicator: Loodin = findViewById(R.id.indicator)

    btnStart.setOnClickListener {
      indicator.startAnim()
    }

    btnStop.setOnClickListener {
      indicator.stopAnim()
    }

    btnAll.setOnClickListener {
      startActivity(Intent(this@MainActivity, AllActivity :: class.java))
    }

  }
}

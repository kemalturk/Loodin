# Loodin
Flexible Kotlin Based Loading Views

[![](https://jitpack.io/v/kmltrk/Loodin.svg)](https://jitpack.io/#kmltrk/Loodin)


## Demo
![Demo](screenshots/g.gif)


## Indicators

**Row 1**
 * `PlusCircleIndicator`
 * `BallPulseIndicator`
 * `BallBarrierJumpingIndicator`
 * `WindTurbineIndicator`
 
## How To Use

Change only indicator parameter

```
  <com.kmltrk.loodinlib.Loodin
    app:indicator="WindTurbineIndicator"
    android:layout_centerInParent="true"
    android:layout_width="150dp"
    android:layout_height="150dp" />

```

You can stop or start animation..

```
    val btnStart: Button = findViewById(R.id.btnStart)
    val btnStop: Button = findViewById(R.id.btnStop)
    val indicator: Loodin = findViewById(R.id.indicator)

    btnStart.setOnClickListener {
      indicator.startAnim()
    }

    btnStop.setOnClickListener {
      indicator.stopAnim()
    }
```

# License

Copyright 2017 Kemal Türk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

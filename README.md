<img width="50%" height="50%" src ="./img/bubble4_nomargin.png" />

[![Build Status](https://travis-ci.org/TouK/bubble.svg?branch=master)](https://travis-ci.org/TouK/bubble)

Bubble is an Android library for obtaining screen orientation. By default, Android supports screen orientation changes by providing a callback. Unfortunately this mechanism has a huge drawback. When system handles the screen orientation change it recreates the view from scratch, making it impossible to create smooth transitions. Moreover if you lock the activity's orientation in AndroidManifest, the callback is not invoked any more. Thus if you would like to have a custom view transition on a screen orientation change, you have to detect it by yourself what is pretty complicated.

Thanks to Bubble you could achieve result as in a camera application. Widgets can be rotated without recreating the whole activity:

<p align="center">
<img src ="https://raw.githubusercontent.com/TouK/bubble/master/readme_assets/sample.gif" />
</p>

### Kotlin
The Library, sample and unit tests are written fully in [Kotlin](https://kotlinlang.org) and Bubble still is fully compatible with typical Java-Andorid projects.

### Import

Import to project:

```gradle
compile 'pl.touk.android:bubble:1.0.0'
```
### Usage
Bubble provides information about screen orientation each time new orientation is detected. Provided orientation is one of the following:
* PORTRAIT
* REVERSE_PORTRAIT
* LANDSCAPE
* REVERSE_LANDSCAPE

To get information from Bubble you have to register for events, and if you do not want to receive events any more, should unregister to avoid memory leaks. Bubble provides two APIs that can be used.

##### RxJava
Please notice sample is written in [Kotlin](https://kotlinlang.org).
```kotlin
class BubbleSampleActivity : AppCompatActivity() {
    val bubble: Bubble = Bubble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_sample)
        textView = findViewById(R.id.label) as TextView
        bubble.register(this)
            .subscribe { bubbleEvent: BubbleEvent -> rotateTo(bubbleEvent.orientation) }
    }
    ...
}
```

#### Callback
Please notice sample is written in [Kotlin](https://kotlinlang.org).
```kotlin
class BubbleSampleActivity : AppCompatActivity() {
    val bubble: Bubble = Bubble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_sample)
        textView = findViewById(R.id.label) as TextView
        bubble.register({ bubbleEvent: BubbleEvent -> rotateTo(bubbleEvent.orientation) }, this)
    }
    ...
}
```

### How does it work
Bubble uses an accelerometer to obtain [Pitch and Roll] of a telephone. An accelerometer is very sensitive so values are bunched and then Bubble produce a new event base on the bunch and the last obtained orientation.

#### License

    Copyright 2015 original author or authors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




   [Kotlin]: <http://kotlinlang.com>
   [Pitch and Roll]: <https://commons.wikimedia.org/wiki/File:Pitch_Roll_and_Yaw.svg>
   [logo]: <./img/bubble.png>

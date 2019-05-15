/*
 * Copyright 2015 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package pl.touk.android.bubble.state

import android.util.Log
import pl.touk.android.bubble.bookkeeper.BookKeeper
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.orientation.Orientation

class BubbleStateMachine {

    var orientation = Orientation.UNDEFINED
//
//    private val data = Array(1000) { 0L }
//    private val rollData = Array(1000) { 0.0F }
//    private val pitchData = Array(1000) { 0.0F }
//    private var eventsCount = 0
//    private var last = System.currentTimeMillis()
//    private val bookKeeper = BookKeeper()

    fun update(coordinates: Coordinates): Boolean {
//        if (eventsCount == 1000) {
//            Log.i("BubbleData", "time ${bookKeeper.calculate(data)}")
//            Log.i("BubbleData", "\troll ${bookKeeper.calculateF(rollData)}")
//            Log.i("BubbleData", "\tpitch ${bookKeeper.calculateF(pitchData)}")
//            eventsCount = 0
//        }
//        data[eventsCount] = System.currentTimeMillis() - last
//        rollData[eventsCount] = coordinates.roll
//        pitchData[eventsCount] = coordinates.pitch
//        last = System.currentTimeMillis()
//        ++eventsCount
        val oldOrientation = orientation
        orientation = Orientation.fetchForRoll(coordinates.roll)
        return oldOrientation != orientation
    }

}
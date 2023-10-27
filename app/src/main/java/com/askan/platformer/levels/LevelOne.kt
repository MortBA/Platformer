package com.askan.platformer.levels

import android.os.SystemClock
import java.util.Random

val RNG = Random(SystemClock.uptimeMillis())
class LevelOne : LevelData() {

    init {
        tileToBitmap.put(NO_TILE, "no_tile")
        tileToBitmap.put(1, PLAYER)
        tileToBitmap.put(2, "ground_left")
        tileToBitmap.put(3, "ground")
        tileToBitmap.put(4, "ground_right")
        tileToBitmap.put(5, "spears_down")
        tileToBitmap.put(6, "coin")
        tileToBitmap.put(7, "flag")
        tileToBitmap.put(8, "shield")
        tileToBitmap.put(9, "movement")

        tiles = arrayOf(
            intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 8, 9),
            intArrayOf(2, 3, 4, 5, 5, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 2, 3, 4, 0, 5, 0, 0, 2, 3, 4, 0, 0, 0, 0, 2, 3, 4, 0, 0, 5, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 5, 0),
            intArrayOf(0),
            intArrayOf(0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 5, 0, 0, 0, 2, 3, 4, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 5, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 3, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(3, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 5, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0)
        )
    }
}
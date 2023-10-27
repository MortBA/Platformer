package com.askan.platformer.levels


class LevelTwo : LevelData() {

    init {
        tileToBitmap.put(NO_TILE, "no_tile")
        tileToBitmap.put(1, PLAYER)
        tileToBitmap.put(2, "mud_left")
        tileToBitmap.put(3, "mud_square")
        tileToBitmap.put(4, "mud_right")
        tileToBitmap.put(5, "spears_down")
        tileToBitmap.put(6, "coin")
        tileToBitmap.put(7, "flag")
        tileToBitmap.put(8, "shield")
        tileToBitmap.put(9, "movement")

        tiles = arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 8, 9),
            intArrayOf(0),
            intArrayOf(3, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 5, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(2, 3, 4, 5, 5, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 2, 3, 4, 0, 5, 0, 0, 2, 3, 4, 0, 0, 0, 0, 2, 3, 4, 0, 0, 5, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 5, 7),
            intArrayOf(0),
            intArrayOf(0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 2, 3, 4, 0, 5, 0, 0, 0, 2, 3, 4, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0),
            intArrayOf(1, 0, 0),
            intArrayOf(3, 0, 5, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 2, 3, 4, 0, 0, 0, 3, 0, 0, 0, 0, 0),
        )
    }
}
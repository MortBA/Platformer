package com.askan.platformer

import android.util.SparseArray

internal const val PLAYER = "blue_left1"
internal const val NULLSPITE = "ground"
internal const val NO_TILE = 0
abstract class LevelData {
    var tiles :Array<IntArray> = emptyArray()
    var tileToBitmap = SparseArray<String>()

    fun getRow(y: Int): IntArray {
        return tiles[y]
    }

    fun getTile(x: Int, y: Int): Int{
        return getRow(y)[x];
    }

    fun getSpriteName(tileType: Int): String{
        val fileName = tileToBitmap[tileType]
        return fileName ?: NULLSPITE
    }

    fun getHeight(): Int {
        return tiles.size
    }
    fun getWidth(): Int {
        return getRow(0).size
    }

}
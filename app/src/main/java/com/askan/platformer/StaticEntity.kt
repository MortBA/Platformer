package com.askan.platformer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint

open class StaticEntity(sprite: String, x: Float, y: Float): Entity() {
    var bitmap: Bitmap
    init {
        this.x = x

        this.y = y
        width = 1.0f
        height = 1.0f
        /*val widthInPixels = engine.worldToScreenX(width)
        val heightInPixels = engine.worldToScreenY(height)*/
        bitmap = engine.pool.createBitmap(sprite, width, height)
    }

    override fun render(canvas: Canvas, transform: Matrix, paint: Paint) {
        canvas.drawBitmap(bitmap,
                transform,
                paint
            )
    }
}
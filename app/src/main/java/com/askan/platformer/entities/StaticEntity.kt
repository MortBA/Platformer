package com.askan.platformer.entities

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.askan.platformer.engine

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
        paint.alpha = 255
        canvas.drawBitmap(bitmap,
                transform,
                paint
            )
    }
}
class Trap(sprite: String, x: Float, y: Float): StaticEntity(sprite, x, y){
    init {
        height = 0.1f
        width = 0.1f
    }
}

class Flag(sprite: String, x: Float, y: Float): StaticEntity(sprite, x, y){
    init {
        height = 0.1f
        width = 0.1f
    }
}
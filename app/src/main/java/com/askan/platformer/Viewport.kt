package com.askan.platformer

import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF

private const val OVERDRAW_BUFFER = 1f //draw this many meters beyond the viewport edges, to avoid visual gaps at the edge of the screen.
class Viewport(
    val screenWidth: Int,
    val screenHeight: Int,
    metersToShowX: Float,
    metersToShowY: Float): Entity() {
    var pixelsPerMeterX = 0
    private var pixelsPerMeterY = 0
    private val screenCenterX = screenWidth / 2
    private val screenCenterY = screenHeight / 2

    init {
        setMetersToShow(metersToShowX, metersToShowY)
        lookAt(2f, 0f)
    }

    //setMetersToShow calculates the number of physical pixels per meters
    //so that we can translate our game world (meters) to the screen (pixels)
    //provide the dimension(s) you want to lock. The viewport will automatically
    // size the other axis to fill the screen perfectly.
    private fun setMetersToShow(metersToShowX: Float, metersToShowY: Float) {
        require(!(metersToShowX <= 0f && metersToShowY <= 0f)) { "One of the dimensions must be provided!" }
        //formula: new height = (original height / original width) x new width
        width = metersToShowX
        height = metersToShowY
        if (metersToShowX == 0f || metersToShowY == 0f) {
            if (metersToShowY > 0f) { //if Y is configured, calculate X
                width= screenWidth.toFloat() / screenHeight * metersToShowY
            } else { //if X is configured, calculate Y
                height = screenHeight.toFloat() / screenWidth * metersToShowX
            }
        }

        pixelsPerMeterX = (screenWidth / width).toInt()
        pixelsPerMeterY = (screenHeight / height).toInt()
    }

    fun lookAt(x: Float, y: Float) {
        setCenter(x, y)
    }

    fun lookAt(e: Entity) {
        lookAt(e.centerX(), e.centerY())
    }

    fun lookAt(pos: PointF) {
        lookAt(pos.x, pos.y)
    }

    fun worldToScreenX(worldDistance: Float) = (worldDistance * pixelsPerMeterX)
    fun worldToScreenY(worldDistance: Float) = (worldDistance * pixelsPerMeterY)

    fun worldToScreen(worldPosX: Float, worldPosY: Float, screenPos: PointF) {
        screenPos.x = (screenCenterX - (centerX() - worldPosX) * pixelsPerMeterX)
        screenPos.y = (screenCenterY - (centerY() - worldPosY) * pixelsPerMeterY)
    }

    fun worldToScreen(worldPos: PointF, screenPos: PointF) {
        worldToScreen(worldPos.x, worldPos.y, screenPos)
    }

    fun worldToScreen(e: Entity, screenPos: PointF) {
        worldToScreen(e.x, e.y, screenPos)
    }

    fun inView(e: Entity): Boolean {
        return isColliding(this, e)
    }

    /*fun inView(bounds: RectF): Boolean {
        val right = lookAt.x + halfDistX
        val left = lookAt.x - halfDistX
        val bottom = lookAt.y + halfDistY
        val top = lookAt.y - halfDistY
        return (bounds.left < right && bounds.right > left
                && bounds.top < bottom && bounds.bottom > top)
    }*/

    override fun toString(): String {
        return "Viewport [${screenWidth}px, ${screenHeight}px / ${width}m, ${height}m]"
    }
}
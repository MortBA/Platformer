package com.askan.platformer

import android.graphics.PointF
import android.util.Log
import com.askan.platformer.entities.Entity
import com.askan.platformer.entities.isColliding

private const val OVERDRAW_BUFFER = 1f //draw this many meters beyond the viewport edges, to avoid visual gaps at the edge of the screen.
private const val CAM_ACCELERATION = 0.05f
private const val MAX_VEL = 5.6f

class Viewport(
    val screenWidth: Int,
    val screenHeight: Int,
    metersToShowX: Float,
    metersToShowY: Float
): Entity() {
    var yBoundryTop = 0f
    var yBoundryBottom = 0f
    var xBoundryEnd = 0f
    var xBoundryStart = 0f
    var pixelsPerMeterX = 0
    private var pixelsPerMeterY = 0
    private val screenCenterX = screenWidth / 2
    private val screenCenterY = screenHeight / 2
    var velX = 1f
    var velY = 1f
    var camX = 0f
    var camY = 0f

    init {
        setMetersToShow(metersToShowX, metersToShowY)
        lookAt(2f, 0f)
    }

    fun setViewBoundry(xBoundryStart: Float, xBoundryEnd: Float, yBoundryTop: Float, yBoundryBottom: Float) {
        this.xBoundryStart = xBoundryStart
        this.xBoundryEnd = xBoundryEnd * pixelsPerMeterX
        this.yBoundryTop = yBoundryTop
        this.yBoundryBottom = yBoundryBottom * pixelsPerMeterY
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

    fun getPixelsPerMeter (): Int {
        return this.pixelsPerMeterX
    }
    fun lookAt(x: Float, y: Float) {
        val xPixel = x * pixelsPerMeterX
        val yPixel = y * pixelsPerMeterY

        if (yPixel - 5 < camY && yPixel + 5 > camY){
            velY = 0f
        }else if (yPixel < camY) {
            if (velY == 0f) {
                velY = 1f
            }
            if (velY < MAX_VEL)
                velY += CAM_ACCELERATION
            camY -= velY
        }else if (yPixel > camY) {
            if (velY == 0f) {
                velY = 1f
            }
            if (velY < MAX_VEL)
                velY += CAM_ACCELERATION
            camY += velY
        }

        if (xPixel - 5 < camX && xPixel + 5 > camX){
            velX = 0f
        }else if (xPixel < camX) {
            if (velX == 0f) {
                velX = 1f
            }
            if (velX < MAX_VEL)
                velX += CAM_ACCELERATION
            camX -= velX
        }else if (xPixel > camX) {
            if (velX == 0f) {
                velX = 1f
            }
            if (velX < MAX_VEL)
                velX += CAM_ACCELERATION
            camX += velX
        }


        Log.v("BOUND", "" + xPixel + " somePixelChar: " + yPixel)
        val boundVals = boundryCheck(camX, camY)
        Log.v("BOUNDRY", "" + boundVals[0] + " some: " + boundVals[1])
        setCenter(boundVals[0]/pixelsPerMeterX, boundVals[1]/pixelsPerMeterY)
    }

    fun lookAt(e: Entity) {
        lookAt(e.centerX(), e.centerY())
    }

    fun lookAt(pos: PointF) {
        lookAt(pos.x, pos.y)
    }

    fun boundryCheck(x: Float, y: Float): FloatArray {

        var boundX = x
        var boundY = y
        if (x <= xBoundryStart) {
            boundX = xBoundryStart
        }
        if (x >= xBoundryEnd) {
            boundX = xBoundryEnd
        }
        if (y <= yBoundryTop) {
            boundY = yBoundryTop
        }
        if (y >= yBoundryBottom) {
            boundY = yBoundryBottom
        }
        Log.v("BOUNDRY", "The boundries X: " + xBoundryEnd  +" Y: "+ yBoundryTop)
        return floatArrayOf(boundX, boundY)
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
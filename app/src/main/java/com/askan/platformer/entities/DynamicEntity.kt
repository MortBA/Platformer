package com.askan.platformer.entities

import android.os.SystemClock
import android.util.Log
import androidx.core.math.MathUtils.clamp
import com.askan.platformer.Jukebox
import com.askan.platformer.engine
import com.askan.platformer.stageHeight
import com.askan.platformer.stageWidth
import java.util.Random

private const val MAX_DELTA = 0.48f
open class DynamicEntity(sprite: String, x: Float, y: Float): StaticEntity(sprite, x, y) {
    val gravity = 40f
    var velX = 0f
    var velY = 0f
    var isOnGround = false
    override fun update(dt: Float) {
        velY += gravity * dt
        y += clamp(velY * dt, -MAX_DELTA, MAX_DELTA)
        x += clamp(velX * dt, -MAX_DELTA, MAX_DELTA)
        if (top() > engine.worldHeight()) {
            setBottom(0f)
        }
        isOnGround = false
    }

    override fun onCollision(that: Entity) {
        getOverlap(this, that, overlap)
        x+= overlap.x
        y+= overlap.y
        if (overlap.y != 0f) {
            velY = 0f
            if (overlap.y < 0f) {
                isOnGround = true
            } //overlap.y > 0f == We've hit our head
        }
    }
}

class Coin(sprite: String, x: Float, y: Float, jukebox: Jukebox): DynamicEntity(sprite, x, y){
    val RNG = Random(SystemClock.uptimeMillis())
    val startX = x
    val startY = y
    init {

        Log.v("COIN", "CREATED")
    }

    override fun onCollision(that: Entity) {
        super.onCollision(that)
        if(that is Player)
            respawn()
    }
    override fun respawn() {
        x = startX
        y = RNG.nextInt(startY.toInt()).toFloat()
    }

    override fun update(dt: Float) {
        super.update(dt)
        velX = -2f
        if(this.x < 0) {
            respawn()
        }
        Log.v("COIN", "Loc: " + this.x + " " + this.y)
    }
}
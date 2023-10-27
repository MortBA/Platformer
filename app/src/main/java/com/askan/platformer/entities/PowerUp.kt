package com.askan.platformer.entities

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.askan.platformer.Jukebox
import com.askan.platformer.R
import com.askan.platformer.SFX
import java.util.Random

open class PowerUp(sprite: String, x: Float, y: Float, jukebox: Jukebox) : DynamicEntity(sprite, x, y){
    val RNG = Random(SystemClock.uptimeMillis())
    val startX = x
    val startY = y
    init {
        Log.v("PowerUp", "Power up spawned")
        respawn()
    }

    final override fun respawn() {
        x = RNG.nextInt(startX.toInt()).toFloat()
        y = RNG.nextInt(startY.toInt()).toFloat()
    }

    override fun onCollision(that: Entity) {
        respawn()
    }
}

class JumpBoost (sprite: String, x: Float, y: Float, jukebox: Jukebox) : PowerUp(sprite, x, y, jukebox){
    val jukebox : Jukebox
    init {
        this.jukebox = jukebox
    }

    override fun onCollision(that: Entity) {
        if(that is Player) {
            that.boostJump()
            jukebox.play(SFX.strength, 0)
        }
        super.onCollision(that)
    }
}

class Invulnerability (sprite: String, x: Float, y: Float, jukebox: Jukebox) : PowerUp(sprite, x, y, jukebox){
    val jukebox : Jukebox
    init {
        this.jukebox = jukebox
    }
    override fun onCollision(that: Entity) {
        if(that is Player) {
            that.invulnerable()
            jukebox.play(SFX.invulnerable, 0)
        }
        super.onCollision(that)
    }
}
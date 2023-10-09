package com.askan.platformer.entities
/*
import android.content.Context
import com.askan.platformer.Jukebox
import com.askan.platformer.R
import com.askan.platformer.RNG
import com.askan.platformer.SFX
import com.askan.platformer.STAGE_HEIGHT
import com.askan.platformer.STAGE_WIDTH
import com.askan.platformer.playerSpeed

const val POWER_UP_SIZE = 50
open class PowerUp(context: Context, jukebox: Jukebox) : BitmapEntity(context, jukebox){

    init {
        respawn()
    }

    final override fun respawn() {
        x = (STAGE_WIDTH + RNG.nextInt(STAGE_WIDTH *2)).toFloat()
        y = RNG.nextInt(STAGE_HEIGHT - POWER_UP_SIZE).toFloat()
    }

    override fun onCollision(that: Entity) {
        respawn()
    }

    override fun update() {
        velX = -playerSpeed
        x += velX
        if(right() < 0) {
            respawn()
        }
    }
}

class Repair (context: Context, jukebox: Jukebox) : PowerUp(context, jukebox){
    init {
        val id = R.drawable.repair
        val bmp = loadBitmap(context.resources, id, POWER_UP_SIZE)
        setSprite(flipVertically(bmp))
    }

    override fun onCollision(that: Entity) {
        if(that is Player) {
            jukebox.play(SFX.repair, 0)
            that.health++
            super.onCollision(that)
        }
    }
}

class Ammo (context: Context, jukebox: Jukebox) : PowerUp(context, jukebox){
    init {
        val id = R.drawable.ammo
        val bmp = loadBitmap(context.resources, id, POWER_UP_SIZE)
        setSprite(flipVertically(bmp))
    }

    override fun onCollision(that: Entity) {
        if(that is Player) {
            jukebox.play(SFX.ammo, 0)
            that.ammo++
            super.onCollision(that)
        }
    }
}*/
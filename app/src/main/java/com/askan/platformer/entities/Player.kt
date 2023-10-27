package com.askan.platformer.entities

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.Log
import com.askan.platformer.GRAVITY
import com.askan.platformer.InputManager
import com.askan.platformer.Jukebox
import com.askan.platformer.SFX
import com.askan.platformer.engine


var PLAYER_RUN_SPEED = 6.0f //meters per second
const val START_HEALTH = 3
var PLAYER_JUMP_FORCE: Float = -(GRAVITY / 2f) //whatever feels good!
val LEFT = 1.0f
val RIGHT = -1.0f
const val ALPHA_OPAQUE = 255
const val EFFECT_DURATION = 3000
const val SECOND = 1000
const val ALPHA_TRANSPARENT = 0
class Player(spriteName: String, xpos: Float, ypos: Float, jukebox: Jukebox) :
    DynamicEntity(spriteName, xpos, ypos) {
    val TAG = "Player"
    private var timeHit : Long = 0
    var facing = LEFT
    var health = START_HEALTH
    var alpha: Int = ALPHA_OPAQUE
    var startX = xpos
    var startY = ypos
    var boostTimeJump : Long = 0
    var boostTimeInvulnerable : Long = 0
    var jukebox : Jukebox
    init {
        this.jukebox = jukebox
    }
    override fun update(dt: Float) {
        val controls: InputManager = engine.getControls()
        val direction: Float = controls._horizontalFactor
        velX = direction * PLAYER_RUN_SPEED
        facing = getFacingDirection(direction)

        if (controls._isJumping && isOnGround) {
            velY = PLAYER_JUMP_FORCE
            isOnGround = false
        }
        effectCheckInvulnerable(boostTimeInvulnerable)
        effectCheckInvulnerable(timeHit)
        effectCheckJump(boostTimeJump)
        Log.v("Player", "Player in Player: " + this.x + " " + this.y)
        super.update(dt) //parent will integrate our velocity and time with our position
    }

    fun effectCheckJump(time: Long) {
        if ((time + EFFECT_DURATION) <= System.currentTimeMillis()) {
            if(PLAYER_JUMP_FORCE != -20f){
                PLAYER_JUMP_FORCE = -20f
                PLAYER_RUN_SPEED = 6f
            }

        }
    }

    fun effectCheckInvulnerable(time: Long) {
        if ((time + EFFECT_DURATION) > System.currentTimeMillis()) {
            if ((System.currentTimeMillis()%5).toInt() == 0) {
                if(alpha == ALPHA_TRANSPARENT) {
                    alpha = ALPHA_OPAQUE
                }else {
                    alpha = ALPHA_TRANSPARENT
                }
            }
        }else {
            alpha = ALPHA_OPAQUE
        }
    }

    private fun getFacingDirection(direction: Float): Float{
        if (direction < 0.0f) {
            return LEFT
        }else if(direction > 0.0f) {
            return RIGHT
        }
        return facing
    }

    override fun render(canvas: Canvas, transform: Matrix, paint: Paint) {
        transform.preScale(facing, 1.0f)
        if (facing == RIGHT) {
            val offset = engine.worldToScreenX(width).toFloat()
            transform.postTranslate(offset, 0.0f)
        }
        paint.alpha = alpha
        canvas.drawBitmap(bitmap,
            transform,
            paint
        )
    }

    override fun onCollision(that: Entity) {
        super.onCollision(that)
        if (that is Trap) {
            if ((timeHit + EFFECT_DURATION) < System.currentTimeMillis() && (boostTimeInvulnerable + EFFECT_DURATION) < System.currentTimeMillis()) {
                timeHit = System.currentTimeMillis()
                health--
                jukebox.play(SFX.hurt, 0)
            }
            ///TODO: ADD SOUND
        }

    }

    override fun respawn() {
        x = startX
        y = startY
    }

    fun boostJump() {
        boostTimeJump = System.currentTimeMillis()
        PLAYER_JUMP_FORCE = -40f
        PLAYER_RUN_SPEED = 12f
        Log.v("Jump", "Jump activated")
    }

    fun invulnerable() {
        boostTimeInvulnerable = System.currentTimeMillis()
        Log.v("Jump", "invol activated")

    }

    fun getXPlayer(): Float {
        return this.x
    }
    fun getYPlayer(): Float {
        return this.y
    }
}
package com.askan.platformer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock.uptimeMillis
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.Random

const val PIXELS_PER_METER = 50
val RNG = Random(uptimeMillis())
lateinit var engine: Game
val paint = Paint()
class Game(context: Context) : SurfaceView(context), Runnable, SurfaceHolder.Callback{
    private val TAG: String = "Game"
    init {
        engine = this
        holder.addCallback(this)
        holder.setFixedSize(getScreenWidth()/2, getScreenHeight()/2)
    }


    private val gameThread = Thread(this)
    @Volatile
    private var isRunning = false
    private var isGameOver = false
    private var levelManager = LevelManager(TestLevel())


    fun worldToScreenX(worldDistance: Float) = (worldDistance * PIXELS_PER_METER).toInt()
    fun worldToScreenY(worldDistance: Float) = (worldDistance * PIXELS_PER_METER).toInt()
    fun screenToWorldX(pixelDistnace: Float) = (pixelDistnace/PIXELS_PER_METER).toFloat()
    fun screenToWorldY(pixelDistnace: Float) = (pixelDistnace/PIXELS_PER_METER).toFloat()


    override fun run() {
        while (isRunning) {
            update()
            render()
        }
    }

    private fun update() {
       //levelManager.update()
    }

    private fun render() {
        val canvas = acquireAndLockCanvas() ?: return
        canvas.drawColor(Color.BLUE)

        for (e in levelManager.entities) {
            e.render(canvas, paint)
        }
        holder.unlockCanvasAndPost(canvas)
    }

    private fun acquireAndLockCanvas(): Canvas? {
        if (holder?.surface?.isValid == false) {
            return null
        }
        return holder.lockCanvas()
    }

    fun pause() {
        Log.d(TAG, "pause")
        isRunning = false
        gameThread.join()
    }

    fun resume() {
        Log.d(TAG, "resume")
        isRunning = true
    }


    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(TAG, "surfaceCreated")
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        Log.d(TAG, "surfaceChanged, width: $width, height: $height")
        Log.d(TAG, "screen width: ${getScreenWidth()}, screen height: ${getScreenHeight()}")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(TAG, "surfaceDestroyed")
    }

    fun getScreenHeight() = context.resources.displayMetrics.heightPixels
    fun getScreenWidth() = context.resources.displayMetrics.widthPixels



}
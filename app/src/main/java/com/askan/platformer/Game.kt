package com.askan.platformer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.os.SystemClock.uptimeMillis
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.Random

//const val PIXELS_PER_METER = 50
const val METERS_TO_SHOW_X = 20f
const val METERS_TO_SHOW_Y = 0F
const val GRAVITY = 40f
const val NANO_TO_SECONDS = 1.0f/1000000000f
val RNG = Random(uptimeMillis())
lateinit var engine: Game
val paint = Paint()
class Game(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), Runnable, SurfaceHolder.Callback {
    private val TAG: String = "Game"
    private val stageHeight = getScreenHeight()/2
    private val stageWidth = getScreenWidth()/2
    private val visibleEntities = ArrayList<Entity>()
    init {
        engine = this
        holder.addCallback(this)
        holder.setFixedSize(stageWidth, stageHeight)
    }


    private val gameThread = Thread(this)
    @Volatile
    private var isRunning = false
    private var isGameOver = false
    val camera = Viewport(stageWidth, stageHeight, METERS_TO_SHOW_X, METERS_TO_SHOW_Y)
    val pool = BitmapPool(this)
    private var levelManager = LevelManager(TestLevel())
    val transform = Matrix()
    val position = PointF()
    var inputs = InputManager()

    fun worldHeight() = levelManager.levelHeight

    fun worldToScreenX(worldDistance: Float) = (worldDistance * camera.pixelsPerMeterX).toInt()
    fun worldToScreenY(worldDistance: Float) = (worldDistance * camera.pixelsPerMeterX).toInt()
    fun screenToWorldX(pixelDistnace: Float) = (pixelDistnace/camera.pixelsPerMeterX).toFloat()
    fun screenToWorldY(pixelDistnace: Float) = (pixelDistnace/camera.pixelsPerMeterX).toFloat()


    override fun run() {
        var lastFrame = System.nanoTime()
        while (isRunning) {
            val deltaTime = (System.nanoTime() - lastFrame) * NANO_TO_SECONDS
            lastFrame = System.nanoTime()
            update(deltaTime)
            //calculate delta time
            //Update all entities with thee dt
            //provide controllers
            //add and remove entities
            //

            buildVisibleSet()
            render(visibleEntities)
        }
    }

    private fun buildVisibleSet() {
        visibleEntities.clear()
        for(e in levelManager.entities) {
            if (camera.inView(e)) {
                visibleEntities.add(e)
            }
        }
    }

    private fun update(deltaTime: Float) {
       levelManager.update(deltaTime)
        camera.lookAt(levelManager.player)
    }

    private fun render(visibleEntities: ArrayList<Entity>) {
        val canvas = acquireAndLockCanvas() ?: return
        canvas.drawColor(Color.BLUE)

        for (e in visibleEntities) {
            transform.reset()
            camera.worldToScreen(e, position)
            transform.postTranslate(position.x.toFloat(), position.y.toFloat())
            e.render(canvas, transform, paint)
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
    fun setControls(input: InputManager) {
        inputs.onPause()
        inputs.onStop()
        inputs = input
        inputs.onResume()
        inputs.onStart()
    }

    fun getControls() = inputs


}
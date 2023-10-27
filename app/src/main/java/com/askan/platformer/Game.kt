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
import com.askan.platformer.entities.BitmapPool
import com.askan.platformer.entities.EFFECT_DURATION
import com.askan.platformer.entities.Entity
import com.askan.platformer.entities.Player
import com.askan.platformer.entities.SECOND
import com.askan.platformer.entities.START_HEALTH
import com.askan.platformer.levels.LevelManager
import com.askan.platformer.levels.LevelOne
import com.askan.platformer.levels.LevelTwo
import com.askan.platformer.levels.PLAYER
import java.util.Random

var PIXELS_PER_METER = 0
const val METERS_TO_SHOW_X = 20f
const val METERS_TO_SHOW_Y = 0F
const val GRAVITY = 40f
const val NANO_TO_SECONDS = 1.0f/1000000000f
val RNG = Random(uptimeMillis())
lateinit var engine: Game
val paint = Paint()
var stageHeight = 0
var stageWidth = 0
var level = 1
var lastFrame : Long = 0
class Game(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), Runnable, SurfaceHolder.Callback {
    private val TAG: String = "Game"
    private var startTime = System.currentTimeMillis()
    private val visibleEntities = ArrayList<Entity>()
    private lateinit var UI: UI
    var player : Player? = null
    private val jukebox = Jukebox(context.assets)
    init {
        stageHeight = getScreenHeight()/2
        stageWidth = getScreenWidth()/2
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
    private var levelManager = LevelManager(this, LevelOne(), SFX.backgroundOne, jukebox)
    private var levelManagerSecond = LevelManager(this, LevelTwo(), SFX.backgroundTwo, jukebox)
    private var levelManagerFirst = LevelManager(this, LevelOne(), SFX.backgroundOne, jukebox)
    val transform = Matrix()
    val position = PointF()
    var inputs = InputManager()
    var deltaTime = 0f


    fun worldHeight() = levelManager.levelHeight

    fun worldToScreenX(worldDistance: Float) = (worldDistance * camera.pixelsPerMeterX).toInt()
    fun worldToScreenY(worldDistance: Float) = (worldDistance * camera.pixelsPerMeterX).toInt()
    fun screenToWorldX(pixelDistnace: Float) = (pixelDistnace/camera.pixelsPerMeterX).toFloat()
    fun screenToWorldY(pixelDistnace: Float) = (pixelDistnace/camera.pixelsPerMeterX).toFloat()

    override fun run() {
        lastFrame = System.nanoTime()
        jukebox.play(SFX.backgroundOne, 1)
        while (isRunning) {
            deltaTime = (System.nanoTime() - lastFrame) * NANO_TO_SECONDS
            lastFrame = System.nanoTime()

            //calculate delta time
            //Update all entities with thee dt
            //provide controllers
            //add and remove entities
            //

            buildVisibleSet()
            render(visibleEntities)
            update(deltaTime)
        }
    }

    private fun buildVisibleSet() {
        visibleEntities.clear()
        for(e in levelManager.entities) {
            if (camera.inView(e)) {
                visibleEntities.add(e)
            }
        }
        camera.setViewBoundry(levelManager.levelWidth.toFloat(), levelManager.totalLevelWidth.toFloat(), levelManager.levelHeight.toFloat(), levelManager.totalLevelBottom.toFloat())
    }

    private fun update(deltaTime: Float) {
        if (!levelManager.loadingEntities) {
            levelManager.update(deltaTime)
            camera.lookAt(levelManager.player)
            Log.v("Player", "Player x: " + levelManager.player.getXPlayer() + "Player y: " + levelManager.player.getYPlayer())
            if (levelManager.player.health == 0) {
                jukebox.play(SFX.dead, 0)
                Log.v("SFX", "The SFX value: " + SFX.dead)
                levelManager.player.respawn()
                levelManager.player.health = START_HEALTH
                levelManager.coinsCollected = 0
                startTime = System.currentTimeMillis()
                if(level == 2) {
                    level = 1
                    jukebox.play(SFX.backgroundOne, 1)
                    levelManager = levelManagerFirst
                }
            }
        }else {
            if (level == 1) {
                level = 2
                levelManager = levelManagerSecond
                jukebox.stop(SFX.backgroundOne)
                jukebox.play(SFX.completionOne, 0)
                jukebox.play(SFX.backgroundTwo, 1)
            }else {
                level = 1
                levelManager = levelManagerFirst
                jukebox.stop(SFX.backgroundTwo)
                jukebox.play(SFX.completionTwo, 0)
                jukebox.play(SFX.backgroundOne, 1)
            }

        }
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

        UI = UI(context, canvas, paint)
        UI.renderHud(isGameOver, ((System.currentTimeMillis() - startTime)/ SECOND).toInt(), levelManager.player.health, levelManager.coinsCollected, stageWidth, stageHeight)

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
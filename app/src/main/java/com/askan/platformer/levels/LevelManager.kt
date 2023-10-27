package com.askan.platformer.levels

import android.util.Log
import com.askan.platformer.Game
import com.askan.platformer.Jukebox
import com.askan.platformer.NANO_TO_SECONDS
import com.askan.platformer.SFX
import com.askan.platformer.entities.Coin
import com.askan.platformer.entities.Entity
import com.askan.platformer.entities.Flag
import com.askan.platformer.entities.Invulnerability
import com.askan.platformer.entities.JumpBoost
import com.askan.platformer.entities.Player
import com.askan.platformer.entities.StaticEntity
import com.askan.platformer.entities.Trap
import com.askan.platformer.entities.isColliding
import com.askan.platformer.lastFrame

class LevelManager(engine : Game, level: LevelData, backgroundMusic: Int, jukebox: Jukebox) {
    var totalLevelBottom = 0
    val entities = ArrayList<Entity>()
    lateinit var coin : Coin
    lateinit var strength : JumpBoost
    lateinit var invulnerable : Invulnerability
    var finishFlag : Flag? = null
    var coinsCollected = 0
    var timeInRun = 0
    val entitiesToAdd = ArrayList<Entity>()
    val entitiesToRemove = ArrayList<Entity>()
    var levelHeight = 0
    var levelWidth = 0
    var totalLevelWidth = 0
    val ENEMY_TILE = 5
    val COIN = 6
    val FLAG = 7
    val INVULNERABLE = 8
    val MOVEMENT = 9
    var engine: Game
    var backgroundMusic = 0
    var loadingEntities = false
    lateinit var player : Player
    var jukebox : Jukebox
    init {
        this.engine = engine
        this.jukebox = jukebox
        this.backgroundMusic = backgroundMusic
        loadAssets(level)
    }

    fun update(dt:Float) {

            for (e in entities) {
                var breakBool = false
                for (eRem in entitiesToRemove){
                    breakBool = eRem == e
                }
                if (breakBool)
                    continue

                e.update(dt)
            }
            doCollisionChecks()
            addAndRemoveEntities()
        engine.deltaTime = (System.nanoTime() - lastFrame) * NANO_TO_SECONDS
    }

    private fun doCollisionChecks() {
        if (loadingEntities) {
            return
        }
        for (e in entities) {
            if (isColliding(player, coin)) {
                coinsCollected++
                player.onCollision(coin)
                coin.onCollision(player)
                jukebox.play(SFX.coin, 0)
            }
            if (isColliding(player, strength)) {
                player.onCollision(strength)
                strength.onCollision(player)
                jukebox.play(SFX.strength, 0)
            }

            if (isColliding(player, invulnerable)) {
                player.onCollision(invulnerable)
                invulnerable.onCollision(player)
                jukebox.play(SFX.invulnerable, 0)
            }

            if (finishFlag?.let { isColliding(it, player) } == true) {
                loadingEntities = true
            }
            if (e == player) {continue}
            if (e == coin) {continue}
            if (e == strength) {continue}
            if (e == invulnerable) {continue}
            if (isColliding(player, e)) {
                player.onCollision(e)
                e.onCollision(player)
                if (e is Trap) {
                    jukebox.play(SFX.hurt, 0)
                    Log.v("SFX","The hurt: " + SFX.hurt)
                }
            }

            if(isColliding(coin, e)){
                coin.onCollision(e)
                e.onCollision(coin)
            }

            if(isColliding(invulnerable, e)){
                invulnerable.onCollision(e)
                e.onCollision(invulnerable)
            }

            if(isColliding(strength, e)){
                strength.onCollision(e)
                e.onCollision(strength)
            }

        }
    }

    private fun loadAssets(level: LevelData) {
        levelHeight = level.getHeight()
        levelWidth = level.getWidth()
        totalLevelBottom = level.tiles.size
        for (y in 0 until levelHeight) {
            val row = level.getRow(y)
            if (row.size > totalLevelWidth) {
                totalLevelWidth = row.size
            }
            for (x in row.indices) {
                val tileID = row[x]
                if (tileID == NO_TILE) continue
                val spriteName = level.getSpriteName(tileID)
                if (tileID == FLAG) {
                    finishFlag = addEntity(Flag(spriteName, x.toFloat(), y.toFloat())) as Flag
                }else if (tileID == ENEMY_TILE) {
                    addEntity(Trap(spriteName, x.toFloat(), y.toFloat()))
                }else if(tileID == COIN) {
                    coin = addEntity(Coin(spriteName, engine.getScreenWidth().toFloat()/engine.camera.pixelsPerMeterX.toFloat(), engine.getScreenHeight().toFloat()/engine.camera.pixelsPerMeterX, jukebox)) as Coin
                }else if (tileID == MOVEMENT) {
                    strength = addEntity(JumpBoost(spriteName, engine.getScreenWidth().toFloat()/engine.camera.pixelsPerMeterX.toFloat(), engine.getScreenHeight().toFloat()/engine.camera.pixelsPerMeterX, jukebox)) as JumpBoost
                }else if (tileID == INVULNERABLE) {
                    invulnerable = addEntity(Invulnerability(spriteName, engine.getScreenWidth().toFloat()/engine.camera.pixelsPerMeterX.toFloat(), engine.getScreenHeight().toFloat()/engine.camera.pixelsPerMeterX, jukebox)) as Invulnerability
                }else{
                    createEntity(spriteName, x, y)
                }
            }
        }

        addAndRemoveEntities()
        loadingEntities = false
    }

    private fun createEntity(spriteName: String, x: Int, y: Int) {

        if (spriteName.equals(PLAYER, ignoreCase = true)) {

            if (engine.player == null) {
                player = Player(spriteName, x.toFloat(), y.toFloat(), jukebox)
                engine.player = player
            }else {
                player = Player(spriteName, x.toFloat(), y.toFloat(), jukebox)
                player.health = engine.player!!.health
                engine.player = player
            }
            addEntity(player)

        }else {
            addEntity(StaticEntity(spriteName, x.toFloat(), y.toFloat()))
        }
    }

    fun addEntity(e: Entity) : Entity {
        entitiesToAdd.add(e)
        return e
    }

    fun removeEntity(e: Entity) {
        entities.remove(e)
    }

    private fun addAndRemoveEntities() {
        for (e in entitiesToRemove) {
            entities.remove(e)
        }
        for (e in entitiesToAdd) {
            entities.add(e)
        }
        entitiesToRemove.clear()
        entitiesToAdd.clear()
    }

    private fun cleanUp() {
        addAndRemoveEntities()
        for(e in entities) {
            e.destroy()
        }
        entities.clear()
    }

    fun destroy() {
        cleanUp()
    }
}
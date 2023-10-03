package com.askan.platformer

import kotlin.properties.Delegates

class LevelManager(level: LevelData) {
    val entities = ArrayList<Entity>()
    lateinit var player : Player
    val entitiesToAdd = ArrayList<Entity>()
    val entitiesToRemove = ArrayList<Entity>()
    var levelHeight = 0

    init {
        loadAssets(level)
    }

    fun update(dt:Float) {
        for (e in entities) {
            e.update(dt)
        }
        doCollisionChecks()
        addAndRemoveEntities()
    }

    private fun doCollisionChecks() {
        for (e in entities) {
            if (e == player) {continue}
            if (isColliding(player, e)) {
                player.onCollision(e)
                e.onCollision(player)
            }
        }
    }

    private fun loadAssets(level: LevelData) {
        levelHeight = level.getHeight()
        val levelWidth = level.getWidth()
        for (y in 0 until levelHeight) {
            val row = level.getRow(y)
            for (x in row.indices) {
                val tileID = row[x]
                if (tileID == NO_TILE) continue
                val spriteName = level.getSpriteName(tileID)
                createEntity(spriteName, x, y)
            }
        }
        addAndRemoveEntities()
    }

    private fun createEntity(spriteName: String, x: Int, y: Int) {

        if (spriteName.equals(PLAYER, ignoreCase = true)) {
            player = Player(spriteName, x.toFloat(), y.toFloat())
            addEntity(player)
        }else {
            addEntity(StaticEntity(spriteName, x.toFloat(), y.toFloat()))
        }
    }

    fun addEntity(e: Entity) {
        entitiesToAdd.add(e)
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
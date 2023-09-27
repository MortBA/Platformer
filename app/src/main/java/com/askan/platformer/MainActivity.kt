package com.askan.platformer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startGame()
    }

    fun startGame() {
        findViewById<Button>(R.id.startGameButton)?.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}
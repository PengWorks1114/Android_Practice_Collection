package com.example.changecolor

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.View
import androidx.collection.arrayMapOf
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var view: View
    private lateinit var button: FloatingActionButton
    private val colors = arrayOf(Color.RED, Color.GRAY, Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.WHITE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        view = findViewById(R.id.view)
        button = findViewById(R.id.button_change)

        button.setOnClickListener{
            view.setBackgroundColor(colors[Random.nextInt(colors.size)])
        }
    }
}
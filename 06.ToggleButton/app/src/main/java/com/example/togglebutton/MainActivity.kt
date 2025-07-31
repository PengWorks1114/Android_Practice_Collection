package com.example.togglebutton

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var toggleButton: ToggleButton
    private lateinit var textViewHide: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toggleButton = findViewById(R.id.toggleButton)
        textViewHide = findViewById(R.id.textView)

        toggleButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                textViewHide.visibility = View.VISIBLE
            } else {
                textViewHide.visibility = View.INVISIBLE
            }
        }

    }
}
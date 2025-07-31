package com.example.radiobutton

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGroup = findViewById(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            radioButton = findViewById(id)

            when(radioButton.id) {
                R.id.yesButton -> {
                    Toast.makeText(this@MainActivity, "Yes button clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.noButton -> {
                    Toast.makeText(this@MainActivity, "No button clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.maybeButton -> {
                    Toast.makeText(this@MainActivity, "Maybe button clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
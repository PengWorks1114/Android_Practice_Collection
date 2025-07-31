package com.example.seekbar

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var textViewProgress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        seekBar = findViewById(R.id.seekBar)
        textViewProgress = findViewById(R.id.textViewProgress)

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Toast.makeText(this@MainActivity, "onProgressChanged",Toast.LENGTH_SHORT).show()
                textViewProgress.text = "Rate =" + seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Toast.makeText(this@MainActivity, "onStartTrackingToch",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Toast.makeText(this@MainActivity, "onStopTakingTouch",Toast.LENGTH_SHORT).show()
                textViewProgress.text = "Final Rate =" + p0!!.progress.toString()

            }

        })
    }
}
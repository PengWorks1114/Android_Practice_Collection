package com.example.app

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var myButton: Button
    private lateinit var myTextView: TextView
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton = findViewById(R.id.myButton)
        myTextView = findViewById(R.id.myTextView)
        editText = findViewById(R.id.editText)

        myButton.text = getString(R.string.button_text)

        myButton.setOnClickListener {
            if(!editText.text.toString().isEmpty()) {
                val input = editText.text.toString()

                myTextView.visibility = View.VISIBLE
                myTextView.text = input
            } else {
                myTextView.visibility = View.VISIBLE
                myTextView.text = getString(R.string.text_view_text_message)

            }

        }


    }

//    fun showMessage(view: View) {
//        myTextView.visibility = View.VISIBLE
//        myTextView.text = "Hello Alex"
//    }


}
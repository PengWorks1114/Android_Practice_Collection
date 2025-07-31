package com.example.checkbox

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var checkBoxKotlin: CheckBox
    private lateinit var checkBoxJava: CheckBox
    private lateinit var checkBoxPython: CheckBox
    private lateinit var textViewChoose: TextView
    private lateinit var showButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkBoxKotlin = findViewById(R.id.checkBoxKotlin)
        checkBoxJava = findViewById(R.id.checkBoxJava)
        checkBoxPython = findViewById(R.id.checkBoxPython)
        textViewChoose = findViewById(R.id.textViewChooise)
        showButton = findViewById(R.id.showButton)

// 設定當按下 showButton 時要執行的動作
        showButton.setOnClickListener {

            // 建立一個 StringBuilder 物件，用來拼接多行文字
            val sb = StringBuilder()

            // 取得 Kotlin CheckBox 的文字與勾選狀態，並加入換行
            sb.append(
                checkBoxKotlin.text.toString() +         // 例如顯示 "Kotlin"
                        " status is: " +                          // 顯示狀態前的說明文字
                        checkBoxKotlin.isChecked + "\n"           // 顯示是否有勾選（true/false），並換行
            )

            // 取得 Java CheckBox 的文字與勾選狀態
            sb.append(
                checkBoxJava.text.toString() +
                        " status is: " +
                        checkBoxJava.isChecked + "\n"
            )

            // 取得 Python CheckBox 的文字與勾選狀態
            sb.append(
                checkBoxPython.text.toString() +
                        " status is: " +
                        checkBoxPython.isChecked + "\n"
            )

            // 最後將拼接好的結果顯示在 textViewChoose 上
            textViewChoose.text = sb.toString()
        }


    }
}

//StringBuilder()用法 / .append
// CheckBox
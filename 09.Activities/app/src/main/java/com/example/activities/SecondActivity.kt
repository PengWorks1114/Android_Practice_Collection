package com.example.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    private lateinit var textViewDataIntent: TextView
    private lateinit var goBackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewDataIntent = findViewById(R.id.textViewData)
        goBackButton = findViewById(R.id.buttonGoBack)
        goBackButton.setOnClickListener {
            val intent = intent
//            取得目前這個 Activity 的啟動 Intent（也就是啟動這個畫面時從上一個畫面傳來的資料）。
//            這裡將它重新命名為變數 intent，以便後續使用。
//            也可以選擇創建新的 Intent，但此處是直接使用原有的。
            setResult(Constants.RESULT_CODE,intent)
//            這是 回傳結果給前一個 Activity 的關鍵方法。
//            Constants.RESULT_CODE：代表你自定義的回傳結果代碼（通常為 int 值，例如 1, 2 等），用於區分不同來源或狀況。
//            intent：回傳給前一個 Activity 的資料內容。
//            呼叫此方法後，前一個畫面中的 startActivityForResult(...) 或 registerForActivityResult(...) 可接收到此結果。
            finish()
        }

        val data = intent.extras

        data?.let {
            val message = data.getString(Constants.INTENT_MESSAGE_KEY)
            val message2 = data.getString(Constants.INTENT_MESSAGE2_KEY)
            val number = data.getString(Constants.INTENT_DATA_NUMBER)

            textViewDataIntent.text = message + "\n" + message2 + "\n" + number
        }
    }
}
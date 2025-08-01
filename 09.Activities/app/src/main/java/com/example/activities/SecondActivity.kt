package com.example.activities // 指定此類別所屬的套件名稱

import android.os.Bundle // 匯入 Bundle 類別，用來處理 Activity 傳入的參數
import android.widget.Button // 匯入按鈕元件類型
import android.widget.TextView // 匯入文字顯示元件類型
import androidx.activity.enableEdgeToEdge // 啟用邊緣延展效果（讓畫面全螢幕顯示）
import androidx.appcompat.app.AppCompatActivity // 匯入支援相容性良好的 Activity 類別
import androidx.core.view.ViewCompat // 處理系統 UI 的相容性處理
import androidx.core.view.WindowInsetsCompat // 用於取得系統 UI 的邊距資訊（狀態列、底部導覽列）

class SecondActivity : AppCompatActivity() {
    private lateinit var textViewDataIntent: TextView // 用來顯示從 MainActivity 傳來的資料
    private lateinit var goBackButton: Button // 返回上一個 Activity 的按鈕

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 啟用畫面邊界延伸（含狀態列/導覽列）

        setContentView(R.layout.activity_second) // 設定版面為 activity_second.xml

        // 設定畫面邊距，避免畫面被狀態列或導覽列遮住
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化元件：畫面上的 TextView 與 Button
        textViewDataIntent = findViewById(R.id.textViewData)
        goBackButton = findViewById(R.id.buttonGoBack)

        // 設定返回按鈕的點擊事件
        goBackButton.setOnClickListener {
            // 使用目前的啟動 Intent 作為回傳資料的容器
            val intent = intent

            // 在原 Intent 中加入額外資料，作為回傳內容
            intent.putExtra(Constants.INTENT_MESSAGE2_KEY, "Hello from the second activity")

            // 設定結果：回傳代碼與資料給前一個畫面
            setResult(Constants.RESULT_CODE, intent)

            // 結束這個 Activity，觸發上一個畫面的 registerForActivityResult 接收回傳
            finish()
        }

        // 取得從 MainActivity 傳來的資料（extras 是一個 Bundle 類型）
        val data = intent.extras

        // 如果資料不為 null，則取出三筆資料並顯示在畫面上
        data?.let {
            val message = data.getString(Constants.INTENT_MESSAGE_KEY) // 第一段訊息
            val message2 = data.getString(Constants.INTENT_MESSAGE2_KEY) // 第二段訊息
            val number = data.getString(Constants.INTENT_DATA_NUMBER) // 數字（這裡應該是 Double，但用 getString 也可）

            // 將三筆資料顯示在 TextView 中，每筆資料換行呈現
            textViewDataIntent.text = "$message\n$message2\n$number"
        }
    }
}

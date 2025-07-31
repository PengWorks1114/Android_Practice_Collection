package com.example.activities // 指定此類別所屬的套件名稱

import android.content.Intent // 匯入 Intent 類別，用來進行畫面之間的跳轉
import android.os.Bundle // 匯入 Bundle 類別，用來處理 Activity 的儲存狀態
import android.widget.Button // 匯入 Button 元件類型
import androidx.activity.enableEdgeToEdge // 匯入系統邊緣延展功能
import androidx.appcompat.app.AppCompatActivity // 匯入 AppCompatActivity，支援相容性較佳的基礎 Activity
import androidx.core.view.ViewCompat // 匯入 ViewCompat，協助處理視圖的相容性操作
import androidx.core.view.WindowInsetsCompat // 匯入 WindowInsetsCompat，用來處理系統 UI（如狀態列）的邊界資訊

class MainActivity : AppCompatActivity() { // 宣告 MainActivity，繼承自 AppCompatActivity
    private lateinit var goToButton: Button // 延遲初始化一個按鈕元件

    override fun onCreate(savedInstanceState: Bundle?) { // Activity 建立時呼叫的生命週期方法
        super.onCreate(savedInstanceState) // 呼叫父類別的 onCreate 方法
        enableEdgeToEdge() // 啟用全螢幕延展樣式（會讓內容延伸至狀態列/導覽列下方）
        setContentView(R.layout.activity_main) // 設定此 Activity 對應的畫面佈局為 activity_main.xml

        // 設定系統 UI 邊距（避免畫面內容被狀態列或導覽列遮住）
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 透過 ID 取得畫面中的按鈕元件
        goToButton = findViewById(R.id.button_go_to_act)

        // 設定按鈕點擊事件
        goToButton.setOnClickListener {
            // 建立一個 Intent，指定要從 MainActivity 切換到 SecondActivity
            val intent = Intent(this@MainActivity, SecondActivity::class.java)

            // 利用 Constants 中的常數，將三筆資料傳給 SecondActivity
            intent.putExtra(Constants.INTENT_MESSAGE_KEY, "Hello from first Activity") // 傳送字串
            intent.putExtra(Constants.INTENT_MESSAGE_KEY2, "How was your day?")        // 傳送另一段字串
            intent.putExtra(Constants.INTENT_DATA_NUMBER, 3.14)                        // 傳送一個數值（Double）

            // 啟動 SecondActivity
            startActivity(intent)
        }
    }
}

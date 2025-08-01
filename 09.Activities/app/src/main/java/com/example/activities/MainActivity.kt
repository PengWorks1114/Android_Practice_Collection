package com.example.activities // 指定此類別所屬的套件名稱

import android.content.Intent // 匯入 Intent 類別，用來啟動其他 Activity 並傳遞資料
import android.os.Bundle // 匯入 Bundle 類別，用來在 Activity 建立時傳遞狀態資料
import android.widget.Button // 匯入 Button 控制元件
import android.widget.TextView // 匯入 TextView 控制元件
import androidx.activity.enableEdgeToEdge // 啟用螢幕延展功能（讓內容延伸至狀態列、導覽列下）
import androidx.activity.result.contract.ActivityResultContracts // 匯入 Jetpack 新式啟動 Activity 的契約類別
import androidx.appcompat.app.AppCompatActivity // 匯入相容版本的 Activity 類別
import androidx.core.view.ViewCompat // 匯入 ViewCompat，用來支援舊版系統的 UI 相容處理
import androidx.core.view.WindowInsetsCompat // 匯入 WindowInsetsCompat，處理系統 UI 邊界（如狀態列）


//原影片在20:20左右的段落
class MainActivity : AppCompatActivity() { // 宣告 MainActivity，繼承自 AppCompatActivity
    private lateinit var goToButton: Button // 延遲初始化按鈕元件，之後在 onCreate 中初始化
    private lateinit var textViewResult: TextView // 延遲初始化文字元件，用來顯示回傳結果

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 呼叫父類別的初始化方法
        enableEdgeToEdge() // 開啟內容延展至系統 UI（狀態列與底部導覽列）

        setContentView(R.layout.activity_main) // 設定使用的版面配置 XML（activity_main.xml）

        // 設定系統 UI 邊距，避免畫面被狀態列或導覽列遮住
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 使用 Jetpack 的新方式註冊啟動其他 Activity 並取得其回傳結果
        val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // 當回傳結果的 resultCode 符合自定義的 RESULT_CODE 常數時執行
            if (result.resultCode == Constants.RESULT_CODE) {
                // 從回傳的 intent 中取出資料（這裡取的是第二個字串參數）
                val message = result.data!!.getStringExtra(Constants.INTENT_MESSAGE2_KEY)
                textViewResult.text = message // 顯示在畫面上
            }
        }

        // 透過 ID 找出畫面中的 TextView 元件，接收回傳的訊息
        textViewResult = findViewById(R.id.textView)

        // 找出按鈕元件，當作跳轉到下一個 Activity 的觸發按鈕
        goToButton = findViewById(R.id.button_go_to_act)

        // 為按鈕設定點擊事件
        goToButton.setOnClickListener {
            // 建立一個 Intent，指定從 MainActivity 切換到 SecondActivity
            val intent = Intent(this@MainActivity, SecondActivity::class.java)

            // 使用 Constants 中定義好的常數名稱，加入要傳送的資料
            intent.putExtra(Constants.INTENT_MESSAGE_KEY, "Hello from first Activity") // 傳送第一段文字
            intent.putExtra(Constants.INTENT_MESSAGE2_KEY, "How was your day?")        // 傳送第二段文字
            intent.putExtra(Constants.INTENT_DATA_NUMBER, 3.14) // 傳送一個 Double 數值

            // 使用 getResult.launch() 方式啟動 SecondActivity，等待回傳結果
            getResult.launch(intent)
        }
    }
}

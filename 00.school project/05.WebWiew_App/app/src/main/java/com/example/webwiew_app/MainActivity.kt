package com.example.webwiew_app

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editUrl: EditText
    private lateinit var checkJs: CheckBox
    private lateinit var btnLoad: Button
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 取得畫面元件參照
        editUrl = findViewById(R.id.editUrl)
        checkJs = findViewById(R.id.checkJs)
        btnLoad = findViewById(R.id.btnLoad)
        webView = findViewById(R.id.webView)

        // 初始讀取 Yahoo 頁面
        loadWebPage("https://news.yahoo.co.jp")

        // 設定按鈕點擊事件
        btnLoad.setOnClickListener {
            val url = editUrl.text.toString()
            if (url.isBlank()) {
                Toast.makeText(this, "請輸入網址！", Toast.LENGTH_SHORT).show()
            } else {
                loadWebPage(url)
            }
        }
    }

    // 封裝 WebView 載入邏輯
    private fun loadWebPage(url: String) {
        webView.webViewClient = WebViewClient()
//        這行指定 WebView 使用一個自訂的 WebViewClient。
//        預設情況下，如果不設定 WebViewClient，當使用者點擊頁面中的連結，系統會改用預設瀏覽器（例如 Chrome）開啟。
//        指定 WebViewClient() 意思是：所有超連結、跳轉都會在同一個 WebView 中處理，而不跳出 App。

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = checkJs.isChecked // 是否啟用 JavaScript
//        webSettings 是操作 WebView 設定用的物件。
//        webSettings.javaScriptEnabled 設定是否啟用 JavaScript。

        webView.loadUrl(url)
    }

    // 處理返回鍵（返回上一頁）
//onBackPressed() 是 Android 中的生命週期方法，當使用者按下裝置的 實體返回鍵（或手勢返回）時會被呼叫。
//加上 override 是因為這是覆寫 AppCompatActivity 原本的行為。
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
//            webView.canGoBack()：判斷 WebView 是否有「瀏覽歷史紀錄」可回上一頁。
//            如果使用者已點選過多個連結，那就有「上一頁」可以返回。
//            webView.goBack()：實際讓 WebView 返回上一頁。
        } else {
            super.onBackPressed()
//            如果 WebView 沒有上一頁可返回（例如剛載入的第一個頁面），則呼叫 super.onBackPressed()。
//            super 代表呼叫原本 Activity 的行為，通常就是 結束當前 Activity（也就是關閉 App 或回前一個畫面）。
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true // 顯示選單
    }
//    這是覆寫 Activity 或 AppCompatActivity 中的生命週期方法。
//    當使用者第一次打開畫面、或按下選單鍵時，這個方法會被呼叫。
//    用來建立畫面右上角的選單內容（通常是三個點點 ⋮ 展開的選單）。
//    menuInflater.inflate(R.menu.options_menu, menu)
//    使用 menuInflater 將指定的選單 XML（這裡是 res/menu/options_menu.xml）
//    轉換成實際可用的選單項目，並加到畫面上的 menu 物件中。
//    R.menu.options_menu 是你在 res/menu/ 資料夾下所建立的 XML 檔案名稱。
//    例如：res/menu/options_menu.xml
//    該 XML 檔會定義多個 <item>，例如 Google、Yahoo、GitHub、Exit 等選項。
//    return true
//    回傳 true 表示：「這個 Activity 想要顯示選單」。



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        當使用者選擇任一選單項目（例如 Google、Yahoo、GitHub、Exit）時，這個方法就會被呼叫。
//        參數 item 是使用者剛剛點擊的選單項目。
        when (item.itemId) {
//                 使用 when 判斷被點擊的選單項目的 ID
//                （來自 res/menu/options_menu.xml 中的 <item android:id="..." />）。
            R.id.menu_google -> {
                loadWebPage("https://www.google.com")
                return true
            }
            R.id.menu_yahoo -> {
                loadWebPage("https://news.yahoo.co.jp")
                return true
            }
            R.id.menu_github -> {
                loadWebPage("https://github.com")
                return true
            }
            R.id.menu_exit -> {
                finish() // 結束應用程式
                return true
            }
        }
        return super.onOptionsItemSelected(item)
//        如果點擊的項目不在 when 條件中（即沒有對應的處理邏輯），則呼叫原本的預設處理方法。
    }



}

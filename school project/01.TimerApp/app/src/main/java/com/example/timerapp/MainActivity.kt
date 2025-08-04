package com.example.timerapp

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var chronometer: Chronometer
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button

    private var running = false
    private var pauseOffset: Long = 0L
    //待修正:新增到毫秒計算 還有record功能

//    lateinit var：延遲初始化變數，會在 onCreate() 中透過 findViewById 初始化。
//    running：布林值，紀錄計時器是否正在執行。
//    pauseOffset：儲存暫停時已經計過的時間，單位為毫秒，用來在暫停後繼續計時。

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得 View 參考
        chronometer = findViewById(R.id.chronometer)
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        btnReset = findViewById(R.id.btn_reset)

        // 設定點擊事件
        btnStart.setOnClickListener { startChronometer() }
        btnStop.setOnClickListener { stopChronometer() }
        btnReset.setOnClickListener { resetChronometer() }

        btnStart.isEnabled = true
        btnStop.isEnabled = false
        btnReset.isEnabled = false

    }
    // 啟動計時器的方法
    private fun startChronometer() {
        // 如果目前尚未啟動計時器
        if (!running) {
            // 設定計時器的基準時間為目前時間 - 已經經過的暫停時間
            // 這樣可以從上次停止的位置繼續計時
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset

            // 啟動計時器
            chronometer.start()

            // 設定狀態為「計時中」
            running = true

            //啟動後,只允許stop 和 reset
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnReset.isEnabled = true

        }
    }

    // 停止計時器的方法
    private fun stopChronometer() {
        // 如果目前正在計時
        if (running) {
            // 停止計時器
            chronometer.stop()

            // 記錄從開始到目前為止經過的時間（用來未來繼續計時）
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base

            // 設定狀態為「未計時」
            running = false

            // 停止後，允許 START 和 RESET，STOP 禁用
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = true
        }
    }

    // 重設計時器的方法
    private fun resetChronometer() {
        // 將基準時間設為現在，這樣畫面會重設為「00:00」
        chronometer.base = SystemClock.elapsedRealtime()

        // 清除暫停時間
        pauseOffset = 0L

        // 如果目前沒有在計時，確保畫面為停止狀態
        if (!running) {
            chronometer.stop()
        }

        // 重設後，只允許 START，其他禁用
        btnStart.isEnabled = true
        btnStop.isEnabled = false
        btnReset.isEnabled = false
    }



}
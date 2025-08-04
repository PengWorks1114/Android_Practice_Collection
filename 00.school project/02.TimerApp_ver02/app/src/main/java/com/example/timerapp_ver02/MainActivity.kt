package com.example.timerapp_ver02

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // 宣告畫面元件
    private lateinit var txtTime: TextView        // 顯示計時結果的文字區塊
    private lateinit var btnStart: Button         // START 按鈕
    private lateinit var btnStop: Button          // STOP 按鈕
    private lateinit var btnReset: Button         // RESET 按鈕
    private lateinit var btnRecord: Button        // RECORD 按鈕
    private lateinit var txtRecord: TextView      // 顯示記錄列表的區塊

    // 控制變數
    private var running = false                   // 是否正在計時
    private var pauseOffset: Long = 0L            // 暫停時的偏移時間（毫秒）
    private var startTime: Long = 0L              // 計時起始時間
    private lateinit var handler: Handler         // 更新時間的排程器
    private lateinit var updateRunnable: Runnable // 定時更新時間顯示的任務

    // 儲存記錄的列表
    private val recordList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化元件
        txtTime = findViewById(R.id.txt_time)
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        btnReset = findViewById(R.id.btn_reset)
        btnRecord = findViewById(R.id.btn_record)
        txtRecord = findViewById(R.id.txt_record)

        // 初始化 Handler
        handler = Handler(mainLooper)

        // 初始按鈕狀態設定
        btnStart.isEnabled = true
        btnStop.isEnabled = false
        btnReset.isEnabled = false

        // START 按鈕事件
        btnStart.setOnClickListener {
            startChronometer()
        }

        // STOP 按鈕事件
        btnStop.setOnClickListener {
            stopChronometer()
        }

        // RESET 按鈕事件
        btnReset.setOnClickListener {
            resetChronometer()
        }

        // RECORD 按鈕事件
        btnRecord.setOnClickListener {
            val currentText = txtTime.text.toString()
            recordList.add(currentText)
            updateRecordText()
        }
    }

    // 啟動計時
    private fun startChronometer() {
        if (!running) {
            startTime = SystemClock.elapsedRealtime() - pauseOffset // 從暫停點接續
            startUpdatingTime()                                     // 開始更新畫面
            running = true

            // 按鈕狀態更新
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnReset.isEnabled = true
        }
    }

    // 停止計時
    private fun stopChronometer() {
        if (running) {
            handler.removeCallbacks(updateRunnable) // 停止更新時間
            pauseOffset = SystemClock.elapsedRealtime() - startTime // 記錄目前已計時時間
            running = false

            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = true
        }
    }

    // 重設計時與記錄
    private fun resetChronometer() {
        handler.removeCallbacks(updateRunnable)   // 停止時間更新
        txtTime.text = "0.00 秒"                  // 重設時間顯示
        pauseOffset = 0L
        running = false

        // 清除記錄列表並更新畫面
        recordList.clear()
        updateRecordText()

        // 重設按鈕狀態
        btnStart.isEnabled = true
        btnStop.isEnabled = false
        btnReset.isEnabled = false
    }

    // 每 100 毫秒更新時間顯示
    private fun startUpdatingTime() {
        updateRunnable = object : Runnable {
            override fun run() {
                val elapsed = SystemClock.elapsedRealtime() - startTime
                val seconds = elapsed / 1000.0
                txtTime.text = String.format("%.2f", seconds) + " 秒"
                handler.postDelayed(this, 100)
            }
        }
        handler.post(updateRunnable)
    }

    // 更新記錄顯示
    private fun updateRecordText() {
        if (recordList.isEmpty()) {
            txtRecord.text = "記錄："
        } else {
            txtRecord.text = "記錄：\n" + recordList.joinToString("\n")
        }
    }
}

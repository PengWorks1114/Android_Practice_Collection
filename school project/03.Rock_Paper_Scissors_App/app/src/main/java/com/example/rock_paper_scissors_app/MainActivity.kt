package com.example.rock_paper_scissors_app

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // ImageView
    private lateinit var imgGu: ImageView
    private lateinit var imgChoki: ImageView
    private lateinit var imgPa: ImageView
    private lateinit var imgCpuHand: ImageView

    // TextView
    private lateinit var txtInfo: TextView
    private lateinit var txtResult: TextView

    // Button
    private lateinit var btnStart: Button
    private lateinit var btnNext: Button

    // 狀態控制
    private var playerHand: Int = -1 // 0=石頭, 1=剪刀, 2=布
    private var winCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View 初期綁定
        imgGu = findViewById(R.id.imgGu)
        imgChoki = findViewById(R.id.imgChoki)
        imgPa = findViewById(R.id.imgPa)
        imgCpuHand = findViewById(R.id.imgCpuHand)

        txtInfo = findViewById(R.id.txtInfo)
        txtResult = findViewById(R.id.txtResult)

        btnStart = findViewById(R.id.btnStart)
        btnNext = findViewById(R.id.btnNext)

        // 畫面初始狀態
        initGame()
    }

    // 初始化畫面
    private fun initGame() {
        txtInfo.text = "何連勝できるかな？"
        txtResult.text = ""

        imgGu.setBackgroundColor(Color.WHITE)
        imgChoki.setBackgroundColor(Color.WHITE)
        imgPa.setBackgroundColor(Color.WHITE)

        imgCpuHand.visibility = View.INVISIBLE

        btnStart.isEnabled = false
        btnNext.isEnabled = false

        // 選擇手勢再啟用
        playerHand = -1
    }
}

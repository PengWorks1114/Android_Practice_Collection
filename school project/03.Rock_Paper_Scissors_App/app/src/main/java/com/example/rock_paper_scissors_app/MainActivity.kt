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

    fun onClickSelectHand(view: View) {
        // 全部重設為白底
        imgGu.setBackgroundColor(Color.WHITE)
        imgChoki.setBackgroundColor(Color.WHITE)
        imgPa.setBackgroundColor(Color.WHITE)

        // 判斷哪一張圖片被點選，改為紅色，並設定選擇編號
        when (view.id) {
            R.id.imgGu -> {
                playerHand = 0
                imgGu.setBackgroundColor(Color.RED)
            }
            R.id.imgChoki -> {
                playerHand = 1
                imgChoki.setBackgroundColor(Color.RED)
            }
            R.id.imgPa -> {
                playerHand = 2
                imgPa.setBackgroundColor(Color.RED)
            }
        }

        // 啟用「勝負！」按鈕
        btnStart.isEnabled = true
    }

    fun onClickStartAction(view: View) {
        // 產生 0~2 的亂數作為 CPU 的手
        val cpuHand = (0..2).random()

        // 顯示對應圖片
        val cpuImageRes = when (cpuHand) {
            0 -> R.drawable.j_gu02
            1 -> R.drawable.j_ch02
            else -> R.drawable.j_pa02
        }
        imgCpuHand.setImageResource(cpuImageRes)
        imgCpuHand.visibility = View.VISIBLE

        // 判斷勝負結果
        judgeJanken(cpuHand)

        // 停用玩家手勢選擇
        imgGu.isEnabled = false
        imgChoki.isEnabled = false
        imgPa.isEnabled = false

        // 按鈕控制
        btnStart.isEnabled = false
        btnNext.isEnabled = true
    }

    private fun judgeJanken(cpuHand: Int) {
        val resultText = when {
            playerHand == cpuHand -> {
                winCount = 0
                "引き分け！"
            }
            (playerHand == 0 && cpuHand == 1) ||
                    (playerHand == 1 && cpuHand == 2) ||
                    (playerHand == 2 && cpuHand == 0) -> {
                winCount++
                "あなたの勝ち！ $winCount 連勝中！"
            }
            else -> {
                winCount = 0
                "あなたの負け！"
            }
        }
        txtResult.text = resultText
    }

    fun onClickNextAction(view: View) {
        // 重設背景顏色
        imgGu.setBackgroundColor(Color.WHITE)
        imgChoki.setBackgroundColor(Color.WHITE)
        imgPa.setBackgroundColor(Color.WHITE)

        // 玩家可以重新點選
        imgGu.isEnabled = true
        imgChoki.isEnabled = true
        imgPa.isEnabled = true

        // 清除結果顯示
        txtResult.text = ""
        txtInfo.text = "何連勝できるかな？"

        // 隱藏 CPU 手勢圖片
        imgCpuHand.visibility = View.INVISIBLE

        // 按鈕狀態
        btnStart.isEnabled = false
        btnNext.isEnabled = false

        // 清除玩家手勢選擇
        playerHand = -1
    }




}

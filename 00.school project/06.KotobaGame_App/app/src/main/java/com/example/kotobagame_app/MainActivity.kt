package com.example.kotobagame_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // 宣告變數: 畫面中的原件
    private lateinit var gridLayout: GridLayout
    private lateinit var edtAnswer: EditText
    private lateinit var btnCheck: Button
    private lateinit var btnGiveUp: Button
    private lateinit var btnNext: Button
    private lateinit var btnRetry: Button
    private var isAnswered = false // 用來判斷是否已答題
    //lateinit 的用途限制 : lateinit 關鍵字只能用在 var 修飾的「非 primitive 型別」變數，例如：
    //可以用在 var textView: TextView
    //❌ 不能用在 Int、Boolean、Double 等原始類型

    // 題目資料 listOf形式
    private val questions = listOf(
        "会計計算総計計量", // 答案：計
        "会釈釈明解釈釈然", // 答案：釈
        "地峡峡谷山峡峡湾", // 答案：峡
        "変装装束外装装着", // 答案：装
        "徴募募兵公募募金", // 答案：募
        "七八八日尺八八九"  // 答案：八
    )
    private var currentIndex = 0 // 現在是第幾題
    private var correctCount = 0 // 答對的題數統計
    private var correctAnswer = "" //現在答案(之後從題目中抓出來)
    private var cellViews = mutableListOf<TextView>() // 儲存所有格子元件,方便控制

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 找到畫面中的元件（透過 ID）
        gridLayout = findViewById(R.id.gridLayout)
        edtAnswer = findViewById(R.id.edtAnswer)
        btnCheck = findViewById(R.id.btnCheck)
        btnGiveUp = findViewById(R.id.btnGiveUp)
        btnNext = findViewById(R.id.btnNext)
        btnRetry = findViewById(R.id.btnRetry)

        // 建立5x5 格子 (TextView)
        createGrid()

        // 顯示第一題
        loadQuestion()

        // 設定按鈕點擊事件
        btnCheck.setOnClickListener {
            checkAnswer()
        }

        btnGiveUp.setOnClickListener {
            Toast.makeText(this, "結束遊戲", Toast.LENGTH_SHORT).show()
            finish() //關閉App
        }

        btnNext.setOnClickListener {
            if (isAnswered) {
                currentIndex++
                loadQuestion()
                isAnswered = false
            } else {
                Toast.makeText(this, "請先完成回答", Toast.LENGTH_SHORT).show()
            }
        }

        btnRetry.setOnClickListener {
            currentIndex = 0
            correctCount = 0
            btnRetry.visibility = View.GONE
            btnCheck.isEnabled = true
            btnNext.isEnabled = true
            loadQuestion()
        }

    }

    private fun createGrid() {
        gridLayout.removeAllViews() // 先清除舊的(如果有的話)

        //設定欄數與列數
        gridLayout.columnCount = 5 //橫向
        gridLayout.rowCount = 5 //縱向

        val textSize = resources.getDimension(R.dimen.question_text_size)

        for (i in 0 until 25) {
            val cell = TextView(this)
            cell.text = ""
            cell.textSize = textSize / resources.displayMetrics.scaledDensity // 單位轉換為 sp
            cell.setPadding(4, 4, 4, 4)
            cell.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

            // 🎨 加框線（可選） - 讓格子比較有分隔感
            cell.setBackgroundResource(android.R.drawable.alert_light_frame)

            cell.layoutParams = GridLayout.LayoutParams().apply {
                width = 120
                height = 120
                rowSpec = GridLayout.spec(i / 5)
                columnSpec = GridLayout.spec(i % 5)
                setMargins(4, 4, 4, 4)
            }

            gridLayout.addView(cell)
            cellViews.add(cell)
        }
    }

    // 載入當前題目並顯示到格子上
    private fun loadQuestion() {
        if (currentIndex >= questions.size) {
            Toast.makeText(this, "全部完成！", Toast.LENGTH_LONG).show()
            finish() // 結束遊戲
            return
        }

        val q = questions[currentIndex] // 取得目前題目
        correctAnswer = q.substring(1, 2) // 正解是第 2 個字

        // 清空輸入框
        edtAnswer.setText("")

        // 將題目字串的每個字依序放入格子（部分位置空白）
        // 教材圖示中安排的位置如下（0~24為格子索引）：
        val map = mapOf(
            1 to q[0].toString(),     // 相
            11 to "↓",                // 箭頭
            10 to q[1].toString(),    // 的
            12 to q[2].toString(),    // →
            13 to "→",                // 箭頭
            14 to q[3].toString(),    // 比
            21 to "↓",                // 箭頭
            23 to q[4].toString(),    // 抵
            24 to q[5].toString()     // 最後一字
        )

        // 依照位置寫入字，其他空白
        for (i in 0 until 25) {
            cellViews[i].text = map[i] ?: ""
        }
    }

    // 檢查是否回答正確
    private fun checkAnswer() {
        val input = edtAnswer.text.toString()

        // 🔒 空白輸入時，不繼續出題
        if (input.isEmpty()) {
            Toast.makeText(this, "請輸入回答", Toast.LENGTH_SHORT).show()
            return
        }

        // ✅ 檢查正確與否
        if (input == correctAnswer) {
            Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show()
            correctCount++ // 累加正答
        } else {
            Toast.makeText(this, "不正確", Toast.LENGTH_SHORT).show()
        }

        // 🔄 準備下一題
        currentIndex++

        // ⛔ 若題目用完，顯示結果並退出
        if (currentIndex >= questions.size) {
            val percent = (correctCount * 100 / questions.size)
            val result = when {
                percent == 100 -> "すごい！漢字博士か？！"
                percent >= 70 -> "不錯，繼續努力！"
                percent >= 40 -> "還可以，再加油！"
                else -> "漢字還需要多練習喔……"
            }

            Toast.makeText(this, "おつかれさまでした\n成績：$correctCount / ${questions.size}\n$result", Toast.LENGTH_LONG).show()

            // 延遲 2 秒結束遊戲
            gridLayout.postDelayed({
                finish()
            }, 2000)
        } else {
            loadQuestion() // 下一題
        }
    }

}
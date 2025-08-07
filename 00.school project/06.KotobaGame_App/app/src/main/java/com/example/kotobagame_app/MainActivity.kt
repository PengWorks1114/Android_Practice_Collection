package com.example.kotobagame_app

import android.os.Bundle
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
    }

    private fun createGrid() {
        gridLayout.removeAllViews() // 先清除舊的(如果有的話)

        //設定欄數與列數
        gridLayout.columnCount = 5 //橫向
        gridLayout.rowCount = 5 //縱向

        for (i in 0 until 25) {
            // 建立新的 TextView 作為格子
            val cell = TextView(this)
            cell.text = "" // 預設先不顯示文字
            cell.textSize = 24f
            cell.setPadding(8, 8, 8, 8)
            cell.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            cell.layoutParams = GridLayout.LayoutParams().apply {
                width = 120
                height = 120
                rowSpec = GridLayout.spec(i / 5)
                //決定這個格子在第幾「列（row）」 -> 當 i = 0~4 ➜ 第 0 列/當 i = 5~9 ➜ 第 1 列
                columnSpec = GridLayout.spec(i % 5)
            }

            // 加入到 GridLayout 與記錄用 List 中
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
            1 to q[0].toString(),   // 相
            11 to q[1].toString(),  // ↓
            10 to q[2].toString(),  // 的
            12 to q[3].toString(),  // →
            13 to q[4].toString(),  // →
            14 to q[5].toString(),  // 比
            21 to q[6].toString(),  // ↓
            23 to q[7].toString(),  // 抵
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
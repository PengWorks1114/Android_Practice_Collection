package com.example.splitbill_app

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // 宣告畫面元件
    private lateinit var edtTotalPay: EditText
    private lateinit var spinnerPeople: Spinner
    private lateinit var btnCalculate: Button
    private lateinit var txtCollectMoney: TextView
    private lateinit var txtMyPay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得元件參照
        edtTotalPay = findViewById(R.id.edtTotalPay)
        spinnerPeople = findViewById(R.id.spinnerPeople)
        btnCalculate = findViewById(R.id.btnCalculate)
        txtCollectMoney = findViewById(R.id.txtCollectMoney)
        txtMyPay = findViewById(R.id.txtMyPay)

        // Spinner 初始化（2~9人）
        val peopleOptions = (2..9).map { "$it 人" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, peopleOptions)
        spinnerPeople.adapter = adapter

        // 按鈕點擊事件
        btnCalculate.setOnClickListener {
            val inputText = edtTotalPay.text.toString()

            if (inputText.isEmpty()) {
                Toast.makeText(this, "請輸入總金額", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalPay = inputText.toIntOrNull()
            val selectedIndex = spinnerPeople.selectedItemPosition
            val number = selectedIndex + 2 // 人數為 2~9 對應位置 0~7

            if (totalPay == null || number <= 1) {
                Toast.makeText(this, "輸入格式錯誤", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doCalculation(totalPay, number)
        }
    }

    // 計算邏輯
    private fun doCalculation(totalPay: Int, number: Int) {
        val collect = ceil(totalPay.toDouble() / number).toInt()   // 每人收取
        val kanjiPay = totalPay - (collect * (number - 1))          // 幹事實際支付

        txtCollectMoney.text = "每人收取金額：${collect} 円"
        txtMyPay.text = "幹事支付金額：${kanjiPay} 円"

        Log.i("splitbill_log", "金額=$totalPay 人數=$number 每人=$collect 幹事=$kanjiPay")
    }
}

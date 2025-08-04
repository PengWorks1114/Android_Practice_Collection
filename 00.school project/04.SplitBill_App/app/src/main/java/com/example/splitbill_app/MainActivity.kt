package com.example.splitbill_app

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.people_array,
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerPeople.adapter = adapter

        // 設定 hint 與按鈕文字（使用 strings.xml）
        edtTotalPay.hint = getString(R.string.hint_total_pay)
        btnCalculate.text = getString(R.string.btn_calculate)

        // 按鈕點擊事件
        btnCalculate.setOnClickListener {
            val inputText = edtTotalPay.text.toString()

            if (inputText.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_input_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalPay = inputText.toIntOrNull()
            val selectedIndex = spinnerPeople.selectedItemPosition
            val number = selectedIndex + 2 // 人數為 2~9 對應位置 0~7

            if (totalPay == null || number <= 1) {
                Toast.makeText(this, getString(R.string.toast_input_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doCalculation(totalPay, number)
        }
    }

    // 使用一般邏輯運算（不用 ceil）進行割り勘計算
    private fun doCalculation(totalPay: Int, number: Int) {
        val base = totalPay / number
        val remainder = totalPay % number
        val collect = if (remainder == 0) base else base + 1
        val kanjiPay = totalPay - (collect * (number - 1))

        txtCollectMoney.text = getString(R.string.txt_collect_money_prefix) + "${collect} 円"
        txtMyPay.text = getString(R.string.txt_kanji_pay_prefix) + "${kanjiPay} 円"

        Log.i("splitbill_log", "金額=$totalPay 人數=$number 每人=$collect 幹事=$kanjiPay")
    }
}

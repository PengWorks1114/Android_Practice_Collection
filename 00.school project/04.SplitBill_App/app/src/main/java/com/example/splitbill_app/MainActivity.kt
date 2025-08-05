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
    private lateinit var radioGroupUnit: RadioGroup
    private lateinit var checkboxKanjiBonus: CheckBox
    private lateinit var toggleSecondParty: ToggleButton




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
        radioGroupUnit = findViewById(R.id.radioGroupUnit)
        checkboxKanjiBonus = findViewById(R.id.checkboxKanjiBonus)
        toggleSecondParty = findViewById(R.id.toggleSecondParty)


        val isKanjiBonus = checkboxKanjiBonus.isChecked


        val selectedUnit = when (radioGroupUnit.checkedRadioButtonId) {
            R.id.radio100 -> 100
            R.id.radio500 -> 500
            R.id.radio1000 -> 1000
            else -> 100 // 預設
        }

// Spinner 初始化（從 strings.xml 的 string-array 讀取）
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

            val selectedIndex = spinnerPeople.selectedItemPosition
            val number = selectedIndex + 2 // 人數為 2~9 對應位置 0~7

            if (inputText.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_input_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalPay = inputText.toIntOrNull()
            if (totalPay == null) {
                Toast.makeText(this, getString(R.string.toast_input_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (totalPay <= 0) {
                Toast.makeText(this, getString(R.string.toast_input_must_be_positive), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (totalPay > 9999999) {
                Toast.makeText(this, getString(R.string.toast_input_too_large), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            doCalculation(totalPay, number)
        }
    }

    private fun doCalculation(totalPay: Int, number: Int) {
        val roundUnit = when {
            findViewById<RadioButton>(R.id.radio500).isChecked -> 500
            findViewById<RadioButton>(R.id.radio1000).isChecked -> 1000
            else -> 100
        }

        val isSecondParty = toggleSecondParty.isChecked
        val isKanjiBonus = findViewById<CheckBox>(R.id.checkboxKanjiBonus).isChecked

        val (perPerson, kanjiPay) = Calculation.calculate(
            totalPay,
            number,
            roundUnit,
            isSecondParty,
            isKanjiBonus
        )

        txtCollectMoney.text = "每人收取金額：${perPerson} 円"
        txtMyPay.text = "幹事支付金額：${kanjiPay} 円"

        Log.i("splitbill_log", "金額=$totalPay 人數=$number 每人=$perPerson 幹事=$kanjiPay")
    }


}

package com.example.quizapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.R
import com.example.quizapp.model.Question
import com.example.quizapp.utils.Constants


class QuestionsActivity : AppCompatActivity(), View.OnClickListener {
    //AppCompatActivity()	是 Android 中提供的 Activity 基底類別，支援舊版相容與 UI 功能
//View.OnClickListener	是 Android 的「點擊事件監聽器介面」
//讓這個類別能用 onClick() 方法處理元件的點擊事件
//    這樣宣告之後，你就可以在這個 Activity 中直接寫：
//    override fun onClick(view: View?) { ... }
//    來統一處理 所有你註冊過的點擊事件，不需要為每個按鈕都寫一個 setOnClickListener { ... } 區塊。
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgress: TextView
    private lateinit var textViewQuestion: TextView
    private lateinit var flagImage: ImageView

    private lateinit var textViewOptionOne: TextView
    private lateinit var textViewOptionTwo: TextView
    private lateinit var textViewOptionThree: TextView
    private lateinit var textViewOptionFour: TextView

    private lateinit var checkButton: Button

    private val currentPosition = 1
    private lateinit var questionsList: MutableList<Question>
    private val selectedOptionPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.text_view_progress)
        textViewQuestion = findViewById(R.id.question_text_view)
        flagImage = findViewById(R.id.image_flag)
        checkButton = findViewById(R.id.button_check)

        textViewOptionOne = findViewById(R.id.text_view_option_one)
        textViewOptionTwo = findViewById(R.id.text_view_option_two)
        textViewOptionThree = findViewById(R.id.text_view_option_three)
        textViewOptionFour = findViewById(R.id.text_view_option_four)

        questionsList = Constants.getQuestions() //呼叫getquestions()
        Log.d("QuestionSize", "${questionsList.size}") //這裡是log回傳

        setQuestion() //這裡才是實際呼叫去執行內容 在下方
    }

    private fun setQuestion() {
        val question = questionsList[currentPosition - 1]

        flagImage.setImageResource(question.image)
        progressBar.progress = currentPosition //progress -1 等於目前題目第幾題 0為第一題 但在UI端顯示1
        textViewProgress.text = "$currentPosition/${progressBar.max}"//這是UI端的幾分之幾題目進度
        textViewQuestion.text = question.question //下面就是各個題的題目
        textViewOptionOne.text = question.optionOne
        textViewOptionTwo.text = question.optionTwo
        textViewOptionThree.text = question.optionThree
        textViewOptionFour.text = question.optionFour

        if(currentPosition == questionsList.size) {
            checkButton.text = "FINISH"
        } else {
            checkButton.text = "CHECK"
        }//當最後一題的時候改顯示成"FINISH"


    }

    override fun onClick(view: View?) {//?問號是允許null型別
//        這一行是在實作 Android 中的點擊事件處理機制，屬於 Kotlin 的函式覆寫（override）語法 + Android 的 View 點擊處理架構。
        when(view?.id){//如果 view 不是 null，就取出它的 id 屬性

        }
    }

}
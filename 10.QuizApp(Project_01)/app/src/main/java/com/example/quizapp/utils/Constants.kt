package com.example.quizapp.utils

import com.example.quizapp.R
import com.example.quizapp.model.Question

object Constants {
    fun getQuestions(): MutableList<Question> {
        val questions = mutableListOf<Question>()

        val quest1 = Question(
            1,
            "What country does this flag belong?",
            R.drawable.italy_flag,
            "Italy", "India", "Iran", "Ireland",
            1
        )
        questions.add(quest1)

        val quest2 = Question(
            2,
            "What country does this flag belong?",
            R.drawable.india_flag,
            "Iran", "Ireland", "India", "Italy",
            3
        )
        questions.add(quest2)

        val quest3 = Question(
            3,
            "What country does this flag belong?",
            R.drawable.france_flag,
            "France", "Finland", "Germany", "Romania",
            1
        )
        questions.add(quest3)

        val quest4 = Question(
            4,
            "What country does this flag belong?",
            R.drawable.germany_flag,
            "Belgium", "Spain", "Germany", "Nigeria",
            3
        )
        questions.add(quest4)

        val quest5 = Question(
            5,
            "What country does this flag belong?",
            R.drawable.brazil_flag,
            "Mexico", "Brazil", "Argentina", "Spain",
            2
        )
        questions.add(quest5)

        val quest6 = Question(
            6,
            "What country does this flag belong?",
            R.drawable.spain_flag,
            "Romania", "Spain", "Germany", "France",
            2
        )
        questions.add(quest6)

        val quest7 = Question(
            7,
            "What country does this flag belong?",
            R.drawable.argentina_flag,
            "Brazil", "Argentina", "Italy", "Finland",
            2
        )
        questions.add(quest7)

        val quest8 = Question(
            8,
            "What country does this flag belong?",
            R.drawable.finland_flag,
            "Norway", "France", "Finland", "Sweden",
            3
        )
        questions.add(quest8)

        val quest9 = Question(
            9,
            "What country does this flag belong?",
            R.drawable.nigeria_flag,
            "Nigeria", "South Africa", "Kenya", "Ghana",
            1
        )
        questions.add(quest9)

        val quest10 = Question(
            10,
            "What country does this flag belong?",
            R.drawable.romania_flag,
            "Romania", "Moldova", "Germany", "Italy",
            1
        )
        questions.add(quest10)

        return questions
    }
}

package com.example.splitbill_app

object Calculation {

    fun calculate(
        totalPay: Int,
        number: Int,
        roundUnit: Int,
        isSecondParty: Boolean,
        isKanjiBonus: Boolean
    ): Pair<Int, Int> {

        return if (isSecondParty) {
            // 🔹 二次會模式：每人金額相同，總額向上補足成可被整除
            val totalWithRounding = ((totalPay + (number * roundUnit) - 1) / (number * roundUnit)) * (number * roundUnit)
            val perPerson = totalWithRounding / number
            val kanjiPay = perPerson  // 幹事與他人相同
            Pair(perPerson, kanjiPay)

        } else {
            // 🔸 一般模式：由幹事補差額
            val base = totalPay / number
            val remainder = totalPay % number
            val rawCollect = if (remainder == 0) base else base + 1
            val perPerson = ((rawCollect + roundUnit - 1) / roundUnit) * roundUnit

            val kanjiPay = if (isKanjiBonus) {
                totalPay - (perPerson * (number - 1))  // 幹事得模式
            } else {
                totalPay - (perPerson * (number - 1))  // 幹事補足
            }

            Pair(perPerson, kanjiPay)
        }
    }
}

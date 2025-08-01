package com.example.activities // 定義這個檔案所屬的套件名稱，方便組織與引用其他類別

// 宣告一個名為 Constants 的類別，專門用來集中管理跨畫面傳值所使用的常數
class Constants {
    companion object {
        // companion object（伴生物件）是一種 Kotlin 特性
        // 作用：讓內部的成員可以像 Java 的 static 變數一樣使用，不需要建立實體就能存取
        // 使用方式：Constants.INTENT_MESSAGE_KEY 這樣即可呼叫

        // 定義傳遞第一段文字訊息時使用的 key，對應 intent.putExtra(...) 的參數名
        const val INTENT_MESSAGE_KEY = "Message"

        // 定義傳遞第二段文字訊息時使用的 key
        const val INTENT_MESSAGE2_KEY = "Message2"

        // 定義傳遞數值資料（如 Double 或 Int）時使用的 key
        const val INTENT_DATA_NUMBER = "Number"

        // 定義一組自訂的回傳結果碼，用來在 registerForActivityResult 中辨識來源與狀態
        const val RESULT_CODE = 1212123
    }
}

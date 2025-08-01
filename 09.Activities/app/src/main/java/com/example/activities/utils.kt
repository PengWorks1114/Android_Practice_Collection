package com.example.activities // 定義這個檔案所屬的套件名稱，方便組織與引用

class Constants { // 定義一個名為 Constants 的類別，用來集中管理常數
    companion object { // companion object 是 Kotlin 的伴生物件，可以讓裡面的成員像靜態變數一樣存取
        const val INTENT_MESSAGE_KEY = "Message"    // 定義一個 Intent 傳值用的常數鍵（例如傳送第一段文字）
        const val INTENT_MESSAGE2_KEY = "Message2"  // 定義另一個 Intent 傳值用的常數鍵（例如傳送第二段文字）
        const val INTENT_DATA_NUMBER = "Number"     // 定義一個 Intent 傳數字資料用的常數鍵（例如傳送數值）
        const val RESULT_CODE = 1212123
    }
}

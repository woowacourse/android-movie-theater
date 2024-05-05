package woowacourse.movie.list.model

import android.util.Log
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Movie(
    val title: String,
    val posterResourceId: Int,
    val firstScreeningDate: LocalDate,
    val runningTime: Int,
    val description: String,
    val id: Long,
) : Serializable {
    val firstScreeningDateFormat: String
        get() = firstScreeningDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val runningTimeString
        get() = runningTime.toString()

    fun aaa() {
        Log.d("alsong", "aaa")
    }
}

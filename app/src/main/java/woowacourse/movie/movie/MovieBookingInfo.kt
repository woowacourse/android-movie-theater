package woowacourse.movie.movie

import java.io.Serializable

data class MovieBookingInfo(
    val title: String,
    val date: String,
    val time: String,
    val ticketCount: Int
) : Serializable {

    fun formatBookingTime(): String {
        val formattedDate: String = date.split("-").joinToString(".")
        return "$formattedDate $time"
    }

    fun formatBookHistoryDate(): String {
        val formattedDate: String = date.split("-").joinToString(".")
        return "$formattedDate | $time"
    }

    fun formatAlarmDate(): String {
        val formattedDate: String = date.split(".").joinToString("-")
        return "$formattedDate $time"
    }

    companion object {
        val dummyData = MovieBookingInfo(
            Movie.dummyData.title,
            "",
            "",
            0
        )
    }
}

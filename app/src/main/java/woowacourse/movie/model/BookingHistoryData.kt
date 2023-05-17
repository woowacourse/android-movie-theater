package woowacourse.movie.model

import java.io.Serializable

data class BookingHistoryData(
    val title: String,
    val date: String,
    val numberOfPeople: Int,
    val seat: List<String>,
    val price: String,
    val theater: String,
) : Serializable, TicketData {

    fun getBookInfo(): String {
        return INFO_FORMATTER.format(numberOfPeople, seat.joinToString(","), theater)
    }

    companion object {
        private const val INFO_FORMATTER = "일반 %d명 | %s | %s"
        val dummyData = BookingHistoryData("데이터를 불러올 수 없습니다.", "", 0, listOf(), "", "")
    }
}

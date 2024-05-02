package woowacourse.movie.reservationresult.uimodel

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

data class ReservationResultUiModel(
    val title: String,
    val cancelDeadLine: String,
    val dateTime: String,
    val bookingDetail: String,
    val totalPrice: PriceUiModel,
) {
    constructor(
        title: String,
        cancelDeadLine: Duration,
        dateTime: LocalDateTime,
        count: Int,
        seats: List<SeatUiModel>,
        totalPrice: Int,
        theaterName: String,
    ) : this(
        title,
        "영화 상영 시작 시간 ${cancelDeadLine.inWholeMinutes}분 전\n까지 취소가 가능합니다",
        dateTime.format(dateFormatter) + " " + dateTime.format(timeFormatter),
        count.toString() + "명"+ " | " + seats.joinToString { it.showPosition } + " | " + theaterName,
        PriceUiModel(totalPrice),
    )

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}

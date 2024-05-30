package woowacourse.movie.domain.model

import woowacourse.movie.data.model.ScreenData
import java.time.LocalDate
import java.time.LocalTime

data class Reservation(
    val id: Int,
    val screenData: ScreenData,
    val ticket: Ticket,
    val seats: Seats,
    val dateTime: DateTime,
    val theater: Theater,
) {
    init {
        require(ticket.count == seats.count()) { "예약된 좌석 수와 티켓 수가 일치하지 않습니다." }
    }

    companion object {
        val NULL =
            Reservation(
                id = -1,
                screenData = ScreenData.NULL,
                ticket = Ticket(1),
                seats =
                    Seats(
                        Seat(Position(0, 0), Grade.S),
                    ),
                dateTime =
                    DateTime(
                        LocalDate.of(2021, 1, 1),
                        LocalTime.of(1, 1),
                    ),
                theater = Theater.NULL,
            )
    }
}

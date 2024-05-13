package woowacourse.movie.repository.db

import woowacourse.movie.domain.model.reservation.seat.SeatingChart
import woowacourse.movie.domain.model.reservation.seat.SelectedSeats

fun ticket(
    ticketId: Long = 0,
    movieTitle: String = "해리포터",
    theaterName: String = "선릉",
    screenDate: String = "2024-05-01",
    screenTime: String = "22:00",
    seats: SelectedSeats =
        SelectedSeats(
            SeatingChart(4, 4),
            1,
        ),
): ReservationTicketEntity {
    return ReservationTicketEntity(
        ticketId,
        movieTitle,
        theaterName,
        screenDate,
        screenTime,
        seats,
    )
}

package woowacourse.movie.model

import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.Seats
import woowacourse.movie.model.seat.index.Col
import woowacourse.movie.model.seat.index.Row
import java.time.LocalDate

val seatA1 = Seat(Row(0), Col(0))
val seatA2 = Seat(Row(0), Col(1))
val seatB3 = Seat(Row(1), Col(2))

val seatsB3 = Seats.create(listOf(seatB3))
val seatsA1B3 = Seats.create(listOf(seatA1, seatB3))
val seatsA1A2B3 = Seats.create(listOf(seatA1, seatA2, seatB3))

val reservationYOURNAME =
    ReservationInfo(
        title = "너의 이름은.",
        date = LocalDate.of(2025, 5, 11),
        time = "18:00",
        seats = seatsB3,
        price = 10000,
        theaterName = "보라매",
    )

val reservationWEATHER =
    ReservationInfo(
        title = "날씨의 아이",
        date = LocalDate.of(2025, 6, 12),
        time = "19:00",
        seats = seatsA1B3,
        price = 20000,
        theaterName = "선릉",
    )

val reservationSUZUME =
    ReservationInfo(
        title = "스즈메의 문단속",
        date = LocalDate.of(2025, 7, 13),
        time = "20:00",
        seats = seatsA1A2B3,
        price = 30000,
        theaterName = "잠실",
    )

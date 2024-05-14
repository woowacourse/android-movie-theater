package woowacourse.movie.reservationlist

import woowacourse.movie.model.MovieReservation
import java.time.format.DateTimeFormatter

fun MovieReservation.toReservationUiModel(theaterName: String) =
    ReservationListUiModel(
        id,
        screenDateTime.format(dateFormatter),
        screenDateTime.format(timeFormatter),
        theaterName,
        movie.title,
    )

private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

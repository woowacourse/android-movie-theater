package woowacourse.movie.domain.model.cinema.ticket

import woowacourse.movie.domain.model.cinema.screen.Seat

class Ticket(
    val seat: Seat,
    val price: Int,
)

fun Seat.toLabel(): String = "${('A' + row)}${col + 1}"

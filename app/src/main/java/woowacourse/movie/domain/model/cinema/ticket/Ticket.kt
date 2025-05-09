package woowacourse.movie.domain.model.cinema.ticket

import woowacourse.movie.domain.model.cinema.screen.Seat

class Ticket(
    val seat: Seat,
    val price: Int,
)

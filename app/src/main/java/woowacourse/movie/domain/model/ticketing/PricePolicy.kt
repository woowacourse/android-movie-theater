package woowacourse.movie.domain.model.ticketing

import woowacourse.movie.domain.model.cinema.SeatType

interface PricePolicy {
    fun calculatePrice(seatType: SeatType): Int
}

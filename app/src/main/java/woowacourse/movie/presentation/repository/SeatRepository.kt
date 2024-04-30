package woowacourse.movie.presentation.repository

import woowacourse.movie.domain.model.reservation.seat.SeatingChart

interface SeatRepository {
    fun getSeatingChart(): SeatingChart
}

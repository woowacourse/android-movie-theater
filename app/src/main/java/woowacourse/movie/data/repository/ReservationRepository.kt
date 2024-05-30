package woowacourse.movie.data.repository

import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TimeReservation

interface ReservationRepository {
    fun savedReservationId(
        screenData: ScreenData,
        seats: Seats,
        dateTime: DateTime,
        theater: Theater,
    ): Result<Long>

    fun savedTimeReservationId(
        screenData: ScreenData,
        count: Int,
        dateTime: DateTime,
    ): Result<Int>

    fun loadAllReservationHistory(): Result<List<ReservationTicket>>

    fun loadTimeReservation(timeReservationId: Int): TimeReservation

    fun findById(id: Int): Result<ReservationTicket>
}

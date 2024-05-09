package woowacourse.movie.usecase

import woowacourse.movie.model.Seats
import woowacourse.movie.repository.ReservationRefRepository

class PutReservationUseCase(
    private val reservationRefRepository: ReservationRefRepository,
) {
    operator fun invoke(
        screeningId: Long,
        seats: Seats,
    ): Long = reservationRefRepository.makeReservationRef(screeningId, seats.toString())
}

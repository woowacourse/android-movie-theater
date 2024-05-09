package woowacourse.movie.usecase

import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seats
import woowacourse.movie.repository.ReservationRefRepository

class FetchAllReservationsUseCase(
    private val reservationRefRepository: ReservationRefRepository,
    private val screeningWithIdUseCase: FetchScreeningWithIdUseCase,
) {
    operator fun invoke(): Result<List<Reservation>> {
        val refs = reservationRefRepository.allReservationRefs()
        return runCatching {
            refs.map {
                val screening = screeningWithIdUseCase(it.screeningId).getOrThrow()
                Reservation(
                    it.id,
                    screening,
                    Seats.from(it.seats),
                )
            }
        }
    }
}

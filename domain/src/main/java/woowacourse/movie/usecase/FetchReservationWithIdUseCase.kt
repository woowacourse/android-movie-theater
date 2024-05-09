package woowacourse.movie.usecase

import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seats
import woowacourse.movie.repository.ReservationRefRepository

class FetchReservationWithIdUseCase(
    private val fetchScreeningWithIdUseCase: FetchScreeningWithIdUseCase,
    private val reservationRefRepository: ReservationRefRepository,
) {
    operator fun invoke(id: Long): Result<Reservation> {
        return runCatching {
            val reservationRef =
                reservationRefRepository.reservationRefById(id) ?: throw NoSuchElementException(
                    NO_RESERVATION,
                )
            val screening =
                fetchScreeningWithIdUseCase(reservationRef.screeningId).getOrNull()
                    ?: throw NoSuchElementException(
                        NO_SCREENING,
                    )
            val seats = Seats.from(reservationRef.seats)
            Reservation(id, screening, seats)
        }
    }

    companion object {
        private const val NO_RESERVATION = "id에 해당하는 reservation이 없습니다"
        private const val NO_SCREENING = "id에 해당하는 screening이 없습니다"
    }
}

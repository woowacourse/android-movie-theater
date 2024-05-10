package woowacourse.movie.presentation.main.util

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatRank
import woowacourse.movie.domain.repository.ReservationRepository
import java.time.LocalDateTime

object DummyReservation : ReservationRepository {
    private val reservations =
        mutableListOf(
            Reservation(
                id = 0L,
                theaterName = "선릉",
                movieTitle = "해리 포터",
                ticketCount = 2,
                seats =
                    listOf(
                        Seat("A", 2, SeatRank.A),
                        Seat("A", 3, SeatRank.A),
                    ),
                dateTime = LocalDateTime.of(2024, 5, 10, 11, 16),
            ),
        )

    override fun saveReservation(reservation: Reservation): Result<Long> {
        return runCatching {
            val id = reservations.size + 1.toLong()
            reservations.add(reservation.copy(id = id))
            id
        }
    }

    override fun findByReservationId(id: Long): Result<Reservation> {
        return runCatching {
            val reservation = reservations.find { reservation -> reservation.id == id }
            reservation ?: throw NoSuchElementException("예약 정보를 찾을 수 없습니다.")
        }
    }

    override fun findAll(): Result<List<Reservation>> {
        return runCatching {
            reservations.toList()
        }
    }
}

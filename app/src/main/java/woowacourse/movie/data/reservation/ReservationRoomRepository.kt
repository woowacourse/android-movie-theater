package woowacourse.movie.data.reservation

import woowacourse.movie.model.ReservationCount
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalTime

object ReservationRoomRepository : ReservationRepository {
    private val reservations = mutableMapOf<Long, Reservation>()

    override fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        reservationCount: ReservationCount,
        theaterName: String,
    ): Long {
        val reservation =
            GenerateReservation.get(
                movieId,
                screeningDate,
                screeningTime,
                reservationCount,
                theaterName,
            )
        reservations[reservation.id] = reservation
        return reservation.id
    }

    override fun find(id: Long): Reservation {
        return reservations[id] ?: throw IllegalArgumentException()
    }

    override fun deleteAll() {
        reservations.clear()
    }
}

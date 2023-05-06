package woowacourse.app.data.reservation

import woowacourse.app.data.movie.MovieDatabase
import java.time.LocalDateTime

object ReservationDatabase : ReservationDataSource {
    private val bookings = mutableListOf<ReservationEntity>(
        ReservationEntity(
            0,
            movie = MovieDatabase.getMovieEntity(1) ?: throw IllegalArgumentException(),
            LocalDateTime.of(2024, 3, 1, 10, 0),
            0,
            listOf<SeatEntity>(SeatEntity(2, 0, 0)),
        ),
    )

    override fun getReservationEntities(): List<ReservationEntity> {
        return bookings.toList()
    }

    override fun getReservationEntity(id: Long): ReservationEntity? {
        return bookings.find { it.id == id }
    }

    override fun addReservationEntity(reservationEntity: ReservationEntity) {
        bookings.add(reservationEntity)
    }

    override fun getNewReservationId(): Long {
        return bookings.last().id + 1
    }
}

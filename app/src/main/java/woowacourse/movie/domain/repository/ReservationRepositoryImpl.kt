package woowacourse.movie.domain.repository

import android.util.Log
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.db.reservationdb.ReservationEntity
import woowacourse.movie.domain.model.Reservation

class ReservationRepositoryImpl(private val dao: ReservationDao) : ReservationRepository {
    override fun saveReservation(reservation: Reservation): Result<Long> {
        return runCatching {
            dao.insert(
                ReservationEntity(
                    reservation.id,
                    reservation.theaterName,
                    reservation.movieTitle,
                    reservation.ticketCount,
                    reservation.seats,
                    reservation.dateTime,
                    reservation.totalPrice,
                ),
            )
        }.onSuccess {
            Log.d("테스트", "new reservationId = $it")
        }.onFailure { e ->
            Log.d("테스트", "${e.message}")
        }
    }

    override fun findByReservationId(id: Long): Result<Reservation> {
        return runCatching {
            val entity = dao.getData(id) ?: throw IllegalArgumentException()
            mapEntityToReservation(entity)
        }
    }

    override fun findAll(): Result<List<Reservation>> {
        return runCatching {
            dao.getAll().map { entity ->
                mapEntityToReservation(entity)
            }
        }
    }

    private fun mapEntityToReservation(entity: ReservationEntity) =
        Reservation(
            entity.uid,
            entity.theaterName,
            entity.movieName,
            entity.ticketCount,
            entity.seats,
            entity.dateTime,
        )
}

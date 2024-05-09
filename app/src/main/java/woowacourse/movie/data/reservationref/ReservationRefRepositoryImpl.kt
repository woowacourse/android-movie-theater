package woowacourse.movie.data.reservationref

import woowacourse.movie.model.ReservationRef
import woowacourse.movie.repository.ReservationRefRepository

class ReservationRefRepositoryImpl(private val reservationRefDao: ReservationRefDao) : ReservationRefRepository {
    override fun makeReservationRef(
        screeningId: Long,
        seats: String,
    ): Long {
        val reservationRefDto =
            ReservationRefDto(
                screeningRefId = screeningId,
                seats = seats,
            )
        return reservationRefDao.insert(reservationRefDto)
    }

    override fun reservationRefById(id: Long): ReservationRef? = reservationRefDao.findById(id)?.toReservationRef()
}

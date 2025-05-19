package woowacourse.movie.view.reservation

import android.content.Context
import woowacourse.movie.data.reservation.ReservationData
import woowacourse.movie.domain.ticket.Reservation

object LocalReservationData : ReservationData {
    private lateinit var reservationDao: ReservationDao

    fun init(applicationContext: Context): LocalReservationData {
        reservationDao = ReservationDatabase.create(applicationContext).reservationDao()
        return this
    }

    override fun add(reservation: Reservation) {
        reservationDao.addReservation(reservation.toEntity())
    }

    override fun reservations(): List<Reservation> =
        reservationDao
            .reservations()
            .map { it.toDomain() }
}

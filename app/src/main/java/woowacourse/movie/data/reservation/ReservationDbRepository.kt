package woowacourse.movie.data.reservation

import android.content.Context
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.reservation.Reservation

class ReservationDbRepository(context: Context) : ReservationRepository {
    private val db = ReservationDbHelper(context)
    override fun add(reservation: Reservation) {
        db.insert(reservation)
    }

    override fun findAll(): List<Reservation> {
        return db.selectReservations()
    }
}

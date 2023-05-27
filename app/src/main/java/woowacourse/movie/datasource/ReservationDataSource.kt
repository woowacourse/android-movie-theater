package woowacourse.movie.datasource

import android.content.Context
import woowacourse.movie.database.ReservationDB
import woowacourse.movie.domain.dataSource.ReservationDataSource
import woowacourse.movie.domain.model.Reservation

class ReservationDataSource(context: Context) :
    ReservationDataSource {

    private val reservationDB = ReservationDB(context)
    override fun getData(): List<Reservation> {
        return reservationDB.getReservationData() ?: listOf()
    }

    override fun addData(data: Reservation) {
        reservationDB.insertData(data)
    }
}

package woowacourse.movie.presenter.reservationDetail

import android.content.Context
import androidx.room.Room
import woowacourse.movie.data.db.AppDatabase
import kotlin.concurrent.thread

class ReservationDetailsPresenter(
    private val view: ReservationDetailsContracts.View,
) : ReservationDetailsContracts.Presenter {
    override fun updateReservationDetails(context: Context) {
        val db =
            Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_data",
                ).build()

        thread {
            val reservationDetails = db.reservationDao().getAll()
            view.showReservationDetails(reservationDetails)
        }
    }
}

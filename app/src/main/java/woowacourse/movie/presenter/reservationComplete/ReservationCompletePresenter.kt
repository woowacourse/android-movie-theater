package woowacourse.movie.presenter.reservationComplete

import android.content.Context
import android.util.Log
import androidx.room.Room
import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.toEntity
import woowacourse.movie.model.ticket.MovieTicket
import kotlin.concurrent.thread

class ReservationCompletePresenter(
    private val view: ReservationCompleteContracts.View,
) : ReservationCompleteContracts.Presenter {
    override fun updateTicketData2(
        movieTicket: MovieTicket,
        context: Context,
    ) {
        val db =
            Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_data",
                ).fallbackToDestructiveMigration()
                .build()
        view.showMovieTicket(movieTicket)
        thread {
            db.reservationDao().insertMovieTicketEntity(movieTicket.toEntity())
            val users = db.reservationDao().getAll()
            users.forEach {
                Log.d("moongchi", "User: $users")
            }
        }
    }

    override fun updateTicketData(movieTicket: MovieTicket) {
        view.showMovieTicket(movieTicket)
    }
}

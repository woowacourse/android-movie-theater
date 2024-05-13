package woowacourse.movie.result.presenter

import android.content.Context
import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.notification.MovieNotificationAlarmManager
import woowacourse.movie.result.presenter.contract.MovieResultContract
import woowacourse.movie.util.Formatter.unFormatColumn
import woowacourse.movie.util.Formatter.unFormatRow
import java.time.LocalDate
import java.time.LocalTime

class MovieResultPresenter(private val movieResultContractView: MovieResultContract.View) :
    MovieResultContract.Presenter {
    private lateinit var reservationHistoryEntity: ReservationHistoryEntity

    override fun loadMovieTicket(
        context: Context,
        ticketId: Long,
    ) {
        val thread =
            Thread {
                reservationHistoryEntity =
                    ReservationHistoryDatabase.getInstance(context).reservationHistoryDao()
                        .findReservationHistoryById(
                            ticketId,
                        )
            }
        thread.start()
        thread.join()

        val movieData = getMovieById(reservationHistoryEntity.movieId)
        movieData?.let { movie ->
            val movieSelectedSeats = MovieSelectedSeats(reservationHistoryEntity.count)
            reservationHistoryEntity.seats.split(", ").forEach { seat ->
                movieSelectedSeats.selectSeat(
                    MovieSeat(
                        seat.unFormatRow(),
                        seat.unFormatColumn(),
                    ),
                )
            }
            movieResultContractView.displayMovieTicket(
                MovieTicket(
                    movie.id,
                    movie.title,
                    LocalDate.parse(reservationHistoryEntity.date),
                    LocalTime.parse(reservationHistoryEntity.time),
                    reservationHistoryEntity.count,
                    movieSelectedSeats,
                    movie.theaters[reservationHistoryEntity.theaterPosition].name,
                ),
            )
        }
    }

    override fun registrationMovieNotification(
        context: Context,
        ticketId: Long,
    ) {
        MovieNotificationAlarmManager.createNotification(context, ticketId)
    }
}

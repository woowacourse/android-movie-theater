package woowacourse.movie.presentation.seats

import android.app.Activity
import woowacourse.movie.data.bookinghistory.BookingHistoryDatabase
import woowacourse.movie.data.bookinghistory.BookingHistoryMapper
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.SelectedSeats
import woowacourse.movie.presentation.notification.NotificationScheduler
import kotlin.concurrent.thread

class SeatsPresenter(
    private val view: SeatsContract.View,
    private val database: BookingHistoryDatabase,
    private val notificationScheduler: NotificationScheduler,
) : SeatsContract.Presenter {
    private lateinit var movieTicket: MovieTicket
    lateinit var selectedSeats: SelectedSeats
        private set

    override fun initializeSeats(movieTicket: MovieTicket) {
        this.movieTicket = movieTicket
        selectedSeats = SelectedSeats(movieTicket.headCount)
        view.initSeats()
        view.showMovieTitle(movieTicket.movieTitle)
        view.updateAmount(movieTicket.amount)
    }

    override fun selectSeat(seat: Seat) {
        runCatching {
            selectedSeats.updateSelection(seat)
            view.updateSelectedSeat(seat, selectedSeats.isSelected(seat))
            view.updateAmount(selectedSeats.getTotalPrice())
            view.updateConfirmButtonEnabled(selectedSeats.isFull())
        }.onFailure {
            view.showMessage(it.message ?: it.stackTraceToString())
        }
    }

    override fun publishMovieTicket() {
        val movieTicket =
            MovieTicket(
                movieTitle = this.movieTicket.movieTitle,
                theaterName = this.movieTicket.theaterName,
                screeningDateTime = this.movieTicket.screeningDateTime,
                headCount = this.movieTicket.headCount,
                amount = selectedSeats.getTotalPrice(),
                seats = selectedSeats.value,
            )
        addToSchedule(movieTicket)
        insertTicket(movieTicket)
        (view as Activity).runOnUiThread {
            view.navigateToSummary(movieTicket)
        }
    }

    override fun restoreSeats(selectedSeats: SelectedSeats?) {
        if (selectedSeats == null) return
        this.selectedSeats = selectedSeats
        selectedSeats.value.forEach { seat -> view.updateSelectedSeat(seat, true) }
        view.updateAmount(selectedSeats.getTotalPrice())
        view.updateConfirmButtonEnabled(selectedSeats.isFull())
    }

    private fun addToSchedule(ticket: MovieTicket) {
        notificationScheduler.schedule(ticket)
    }

    private fun insertTicket(ticket: MovieTicket) {
        thread {
            val dao = database.bookingHistoryDao()
            dao.insert(BookingHistoryMapper.mapToBookingHistory(ticket))
        }
    }
}

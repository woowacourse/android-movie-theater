package woowacourse.movie.view.reservation.detail

import android.os.Bundle
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Ticket
import woowacourse.movie.view.home.movies.toMovieUi
import woowacourse.movie.view.home.theater.Showing
import woowacourse.movie.view.reservation.toUi
import java.time.LocalDateTime

class ReservationPresent(
    private val view: ReservationContract.View,
) : ReservationContract.Presenter {
    private var count: Int = DEFAULT_PERSONNEL
    private var selectedDatePosition = DEFAULT_DATE_POSITION
    private var selectedTimePosition = DEFAULT_TIME_POSITION
    private lateinit var movie: Movie

    override fun fetchData(
        movie: Movie,
        showings: Showing,
    ) {
        this.movie = movie
        view.showMovieReservationScreen(this.movie.toMovieUi())
        view.setReservationButton(showings)
        view.showSpinnerData(this.movie, selectedDatePosition, showings)
        view.showCount(count)
    }

    override fun onSaveState(outState: Bundle) {
        outState.putInt(KEY_PERSONNEL_COUNT, count)
        outState.putInt(KEY_DATE_POSITION, selectedDatePosition)
        outState.putInt(KEY_TIME_POSITION, selectedTimePosition)
    }

    override fun onRestoreState(outState: Bundle) {
        count = outState.getInt(KEY_PERSONNEL_COUNT)
        selectedDatePosition = outState.getInt(KEY_DATE_POSITION)
        selectedTimePosition = outState.getInt(KEY_TIME_POSITION)
        view.showCount(count)
    }

    override fun increasedCount() {
        count++
        view.showCount(count)
    }

    override fun decreasedCount() {
        if (count > 1) {
            count--
        }
        view.showCount(count)
    }

    override fun selectedDate(position: Int) {
        this.selectedDatePosition = position
        view.setTimeSelection(selectedTimePosition)
    }

    override fun selectedTime(position: Int) {
        this.selectedTimePosition = position
    }

    override fun resetSelectedTimePosition(position: Int) {
        if (selectedDatePosition != position) {
            selectedTimePosition = 0
        }
    }

    override fun createTicket(
        selectedDateTime: LocalDateTime,
        theaterName: String,
    ) {
        val ticketUi =
            Ticket(
                title = movie.title,
                date = selectedDateTime,
                personnel = count,
                theaterName = theaterName,
            ).toUi()
        view.navigateToReservationComplete(ticketUi)
    }

    companion object {
        private const val KEY_PERSONNEL_COUNT = "personnel_count"
        private const val KEY_DATE_POSITION = "movieDate_position"
        private const val KEY_TIME_POSITION = "timeTable_position"
        private const val DEFAULT_PERSONNEL = 1
        private const val DEFAULT_DATE_POSITION = 0
        private const val DEFAULT_TIME_POSITION = 0
    }
}

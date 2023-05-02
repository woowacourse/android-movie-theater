package woowacourse.movie.presentation.activities.ticketing.presenter

import android.os.Bundle
import woowacourse.movie.domain.model.movie.DomainMovieDate
import woowacourse.movie.domain.model.movie.DomainMovieTime
import woowacourse.movie.presentation.activities.ticketing.contract.TicketingContract
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.movieitem.Movie

class TicketingPresenter(
    view: TicketingContract.View,
    private var movie: Movie,
    private var movieTicket: Ticket = Ticket(),
    private var selectedDate: MovieDate? = null,
    private var selectedTime: MovieTime? = null,
) : TicketingContract.Presenter(view) {

    private val movieDates: List<MovieDate> = DomainMovieDate.releaseDates(
        from = movie.startDate,
        to = movie.endDate
    ).map { it.toPresentation() }

    private val movieTimes = mutableListOf<MovieTime>()

    override fun onCreate() {
        view.initView(movieDates)
    }

    override fun onSaveState(outState: Bundle) {
        view.saveViewState(outState, movieTicket, selectedDate, selectedTime)
    }

    override fun onRestoreState(movieTicket: Ticket, movieDate: MovieDate, movieTime: MovieTime) {
        this.movieTicket = movieTicket
        this.selectedDate = movieDate
        this.selectedTime = movieTime
        updateRunningTimes(selectedDate)

        val movieDatePos = movieDates.indexOf(selectedDate)
        val movieTimePos = movieTimes.indexOf(selectedTime)

        view.restoreViewState(movieTicket.count, movieDatePos, movieTimePos)
    }

    override fun plusCount() {
        movieTicket = (movieTicket.toDomain() + 1).toPresentation()
        view.updateCount(movieTicket.count)
    }

    override fun minusCount() {
        movieTicket = (movieTicket.toDomain() - 1).toPresentation()
        view.updateCount(movieTicket.count)
    }

    override fun onClickTicketingButton() {
        if (selectedDate == null || selectedTime == null) {
            view.showUnSelectDateTimeAlertMessage()
            return
        }

        view.startSeatPickerActivity(movie, movieTicket, selectedDate!!, selectedTime!!)
    }

    override fun onSelectMovieDate(position: Int) {
        selectedDate = movieDates[position]
        selectedTime = movieTimes.firstOrNull()
        updateRunningTimes(selectedDate)
    }

    override fun onSelectMovieTime(position: Int) {
        selectedTime = movieTimes.getOrNull(position)
    }

    private fun updateRunningTimes(selectedDate: MovieDate?) {
        selectedDate?.toDomain()?.run {
            val newRunningTimes =
                DomainMovieTime.runningTimes(isWeekend(), isToday()).map { it.toPresentation() }
            movieTimes.clear()
            movieTimes.addAll(newRunningTimes)
            view.updateRunningTimes(movieTimes)
        }
    }
}

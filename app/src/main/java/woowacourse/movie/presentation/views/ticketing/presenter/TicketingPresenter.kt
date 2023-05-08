package woowacourse.movie.presentation.views.ticketing.presenter

import woowacourse.movie.domain.model.movie.DomainMovieDate
import woowacourse.movie.domain.model.movie.DomainMovieTime
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.views.ticketing.contract.TicketingContract

class TicketingPresenter(
    view: TicketingContract.View,
    private var state: TicketingState,
) : TicketingContract.Presenter(view) {
    private val movieDates: List<MovieDate> = DomainMovieDate.releaseDates(
        from = state.movie.startDate,
        to = state.movie.endDate
    ).map { it.toPresentation() }

    private val movieTimes = mutableListOf<MovieTime>()

    init {
        view.initView(state.movie, movieDates)
    }

    override fun getState(): TicketingState = state.copy()

    override fun setState(ticketingState: TicketingState) {
        this.state = ticketingState.copy()
        updateRunningTimes(ticketingState.movieDate)

        val movieDatePos = movieDates.indexOf(ticketingState.movieDate)
        val movieTimePos = movieTimes.indexOf(ticketingState.movieTime)

        view.showTicketingState(ticketingState.ticketCount, movieDatePos, movieTimePos)
    }

    override fun plusCount() {
        state = state.copy(ticket = (state.ticket.toDomain() + 1).toPresentation())
        view.updateCount(state.ticketCount)
    }

    override fun minusCount() {
        state = state.copy(ticket = (state.ticket.toDomain() - 1).toPresentation())
        view.updateCount(state.ticketCount)
    }

    override fun onClickTicketingButton() {
        if (state.isNotSelectedDateTime) {
            view.showUnSelectDateTimeAlertMessage()
            return
        }
        view.showSeatPickerScreen(getState())
    }

    override fun onSelectMovieDate(position: Int) {
        state = state.copy(movieDate = movieDates[position], movieTime = movieTimes.firstOrNull())
        updateRunningTimes(state.movieDate)
    }

    override fun onSelectMovieTime(position: Int) {
        state = state.copy(movieTime = movieTimes.getOrNull(position))
    }

    private fun updateRunningTimes(movieDate: MovieDate?) {
        movieDate?.toDomain()?.run {
            val newRunningTimes =
                DomainMovieTime.runningTimes(isWeekend(), isToday()).map { it.toPresentation() }
            movieTimes.clear()
            movieTimes.addAll(newRunningTimes)
            view.updateRunningTimes(movieTimes)
        }
    }
}

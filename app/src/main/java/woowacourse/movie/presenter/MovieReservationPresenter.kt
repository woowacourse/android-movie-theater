package woowacourse.movie.presenter

import domain.Count
import domain.movieTimePolicy.MovieTime
import domain.movieTimePolicy.WeekdayMovieTime
import domain.movieTimePolicy.WeekendMovieTime
import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.mapper.TheaterMapper.toDomain
import java.time.LocalDate

class MovieReservationPresenter(
    val view: MovieReservationContract.View,
    ticketCount: Int,
) : MovieReservationContract.Presenter {
    private var count = Count(ticketCount)
    override fun updateDateSpinner(theaterUiModel: TheaterUiModel) {
        val theater = theaterUiModel.toDomain()
        val dates = theater.getAllScreenDates()
        view.setDateSpinner(dates)
    }

    override fun onSelectDate(theaterUiModel: TheaterUiModel, date: LocalDate) {
        val theater = theaterUiModel.toDomain()
        val screenTimes = theater.getScreenTimesOnDate(date)
        view.setTimeSpinner(screenTimes)
    }

    override fun onPlusTicketCount() {
        count += PLUS_VALUE
        view.setCounterText(count.value)
    }

    override fun onMinusTicketCount() {
        count -= MINUS_VALUE
        view.setCounterText(count.value)
    }

    override fun onReservationButtonClick() {
        view.startSeatSelectActivity(count.value)
    }

    companion object {
        private const val PLUS_VALUE = 1
        private const val MINUS_VALUE = 1
    }

}

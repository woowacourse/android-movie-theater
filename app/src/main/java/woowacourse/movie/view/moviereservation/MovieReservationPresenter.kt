package woowacourse.movie.view.moviereservation

import domain.Count
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
        view.setDates(dates)
    }

    override fun updateTimes(theaterUiModel: TheaterUiModel, date: LocalDate) {
        val theater = theaterUiModel.toDomain()
        val screenTimes = theater.getScreenTimesOnDate(date)
        view.setTimes(screenTimes)
    }

    override fun plusTicketCount() {
        count += PLUS_VALUE
        view.setCounterText(count.value)
    }

    override fun minusTicketCount() {
        count -= MINUS_VALUE
        view.setCounterText(count.value)
    }

    override fun moveNextReservationStep() {
        view.showSelectSeatScreen(count.value)
    }

    companion object {
        private const val PLUS_VALUE = 1
        private const val MINUS_VALUE = 1
    }
}

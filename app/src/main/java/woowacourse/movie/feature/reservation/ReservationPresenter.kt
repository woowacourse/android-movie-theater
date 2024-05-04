package woowacourse.movie.feature.reservation

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.HeadCount.Companion.DEFAULT_HEAD_COUNT

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
    private val movieId: Int,
    private val theaterId: Int,
    savedHeadCount: Int = DEFAULT_HEAD_COUNT,
) : ReservationContract.Presenter {
    private val headCount = HeadCount(savedHeadCount)

    override fun loadMovie() {
        val movie = screeningDao.find(movieId)
        view.showMovieInformation(movie)
    }

    override fun loadScreeningPeriod() {
        val movie = screeningDao.find(movieId)
        view.showScreeningPeriod(movie)
    }

    override fun loadScreeningTimes(selectedDate: String) {
        val theaterTimes = theaterDao.findScreeningTimes(theaterId)
        view.showScreeningTimes(theaterTimes, selectedDate)
    }

    override fun increaseHeadCount() {
        val result = headCount.increase()
        handleHeadCountBounds(result)
    }

    override fun decreaseHeadCount() {
        val result = headCount.decrease()
        handleHeadCountBounds(result)
    }

    override fun sendTicketToSeatSelection(dateTime: ScreeningDateTime) {
        view.navigateToSeatSelection(dateTime, movieId, theaterId, headCount)
    }

    override fun handleHeadCountBounds(result: ChangeTicketCountResult) {
        when (result) {
            is Success -> view.changeHeadCount(headCount.count)
            is Failure -> view.showResultToast()
        }
    }

    override fun restoreHeadCount() {
        view.changeHeadCount(headCount.count)
    }
}

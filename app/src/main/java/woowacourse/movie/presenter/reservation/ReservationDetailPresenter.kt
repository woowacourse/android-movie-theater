package woowacourse.movie.presenter.reservation

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success
import woowacourse.movie.model.ticket.HeadCount

class ReservationDetailPresenter(
    private val view: ReservationDetailContract.View,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
) : ReservationDetailContract.Presenter {
    var headCount = HeadCount()
        private set

    override fun loadMovie(movieId: Int) {
        val movie = screeningDao.find(movieId)
        view.showMovieInformation(movie)
    }

    override fun loadScreeningPeriod(movieId: Int) {
        val movie = screeningDao.find(movieId)
        view.showScreeningPeriod(movie)
    }

    override fun loadScreeningTimes(
        theaterId: Int,
        selectedDate: String,
    ) {
        val theaterTimes = theaterDao.findScreeningTimes(theaterId)
        view.showScreeningTimes(theaterTimes, selectedDate)
    }

    override fun increaseHeadCount(count: Int) {
        headCount = HeadCount(count)
        val result = headCount.increase()
        handleHeadCountBounds(result)
    }

    override fun decreaseHeadCount(count: Int) {
        headCount = HeadCount(count)
        val result = headCount.decrease()
        handleHeadCountBounds(result)
    }

    override fun initializeReservationButton(dateTime: ScreeningDateTime) {
        view.navigateToSeatSelection(dateTime, headCount)
    }

    override fun handleHeadCountBounds(result: ChangeTicketCountResult) {
        when (result) {
            is Success -> view.changeHeadCount(headCount.count)
            is Failure -> view.showResultToast()
        }
    }
}

package woowacourse.movie.presenter.reservation

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success
import woowacourse.movie.model.ticket.Ticket

class ReservationDetailPresenter(
    private val view: ReservationDetailContract.View,
    private val dao: ScreeningDao,
) : ReservationDetailContract.Presenter {
    var headCount = HeadCount()
        private set

    var ticket = Ticket()
        private set

    override fun loadMovie(movieId: Int) {
        val movie = dao.find(movieId)
        view.showMovieInformation(movie)
    }

    override fun loadScreeningPeriod(movieId: Int) {
        val movie = dao.find(movieId)
        view.showScreeningPeriod(movie)
    }

    override fun loadScreeningTimes(
        movieId: Int,
        selectedDate: String,
    ) {
        val movie = dao.find(movieId)
        view.showScreeningTimes(movie, selectedDate)
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

    override fun initializeReservationButton(
        movieId: Int,
        dateTime: ScreeningDateTime,
    ) {
        view.navigateToSeatSelection(movieId, dateTime, headCount)
    }

    override fun handleHeadCountBounds(result: ChangeTicketCountResult) {
        when (result) {
            is Success -> view.changeHeadCount(headCount.count)
            is Failure -> view.showResultToast()
        }
    }
}

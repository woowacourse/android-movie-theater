package woowacourse.movie.feature.reservation

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success
import woowacourse.movie.model.theater.Theater.Companion.DEFAULT_THEATER_ID
import woowacourse.movie.model.ticket.HeadCount
import java.time.LocalDate
import java.time.LocalTime

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
    private val movieId: Int,
    private val theaterId: Int,
    savedHeadCount: Int,
) : ReservationContract.Presenter {
    private val headCount = HeadCount(savedHeadCount)

    init {
        view.changeHeadCount(savedHeadCount)
    }

    override fun loadMovie() {
        val movie = screeningDao.find(movieId)
        view.showMovieInformation(movie)
    }

    override fun loadScreeningPeriod() {
        val movie = screeningDao.find(movieId)
        view.showScreeningPeriod(movie)
    }

    override fun loadScreeningTimes(selectedDate: String) {
        val theaterTimes: List<LocalTime> = theaterDao.findScreeningTimesByMovieId(theaterId, movieId)
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

    override fun sendTicketToSeatSelection() {
        val date: LocalDate = view.getScreeningDate()
        val time: LocalTime = view.getScreeningTime()
        val dateTime = ScreeningDateTime(date, time)
        view.showDateTime(dateTime)
        view.navigateToSeatSelection(dateTime, movieId, theaterId, headCount)
    }

    override fun handleUndeliveredData() {
        if (movieId == DEFAULT_MOVIE_ID ||
            theaterId == DEFAULT_THEATER_ID
        ) {
            view.showErrorSnackBar()
        }
    }

    private fun handleHeadCountBounds(result: ChangeTicketCountResult) {
        when (result) {
            is Success -> view.changeHeadCount(headCount.count)
            is Failure -> view.showResultToast()
        }
    }
}

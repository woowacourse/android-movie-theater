package woowacourse.movie.feature.reservation

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success
import woowacourse.movie.model.theater.Theater.Companion.DEFAULT_THEATER_ID
import woowacourse.movie.model.ticket.HeadCount
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationPresenter(
    private val view: ReservationContract.View,
    screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
    private val movieId: Int,
    private val theaterId: Int,
    savedHeadCount: Int,
) : ReservationContract.Presenter {
    private val headCount = HeadCount(savedHeadCount)
    private val movie: Movie = screeningDao.find(movieId)
    private val screeningDates: List<LocalDate> = movie.screeningPeriod
    private lateinit var screeningTimes: List<LocalTime>
    private var screeningDateId: Long = 0
    private var screeningTimeId: Long = 0

    init {
        view.changeHeadCount(savedHeadCount)
    }

    override fun loadMovieInformation() {
        if (movieId != DEFAULT_MOVIE_ID &&
            theaterId != DEFAULT_THEATER_ID
        ) {
            loadMovie()
            loadScreeningDates()
            loadScreeningTimes()
        } else {
            handleUndeliveredData()
        }
    }

    override fun selectScreeningDate(selectedDateId: Long) {
        screeningDateId = selectedDateId
        view.showScreeningDate(screeningDateId)
    }

    override fun selectScreeningTime(selectedTimeId: Long) {
        screeningTimeId = selectedTimeId
        view.showScreeningTime(screeningTimeId)
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
        val screeningDate = screeningDates[screeningDateId.toInt()]
        val screeningTime = screeningTimes[screeningTimeId.toInt()]
        val dateTime = LocalDateTime.of(screeningDate, screeningTime)
        view.showDateTime(dateTime)
        view.navigateToSeatSelection(dateTime, movieId, theaterId, headCount)
    }

    private fun handleUndeliveredData() {
        view.showErrorSnackBar()
    }

    private fun loadMovie() {
        view.showMovieInformation(movie)
    }

    private fun loadScreeningDates() {
        view.showScreeningDates(screeningDates)
    }

    private fun loadScreeningTimes() {
        screeningTimes = theaterDao.findScreeningTimesByMovieId(theaterId, movieId)
        view.showScreeningTimes(screeningTimes)
    }

    private fun handleHeadCountBounds(result: ChangeTicketCountResult) {
        when (result) {
            is Success -> view.changeHeadCount(headCount.count)
            is Failure -> view.showResultToast()
        }
    }
}

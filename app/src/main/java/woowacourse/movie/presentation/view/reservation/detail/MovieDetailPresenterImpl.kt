package woowacourse.movie.presentation.view.reservation.detail

import android.util.Log
import woowacourse.movie.repository.MovieRepositoryImpl
import woowacourse.movie.data.repository.ReservationMovieInfoRepositoryImpl
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationMovieInfo
import woowacourse.movie.presentation.repository.MovieRepository
import woowacourse.movie.presentation.repository.ReservationMovieInfoRepository
import woowacourse.movie.presentation.repository.TheaterRepository
import woowacourse.movie.presentation.uimodel.MovieUiModel
import woowacourse.movie.repository.TheaterRepositoryImpl
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailPresenterImpl(
    val movieId: Int,
    val theaterId: Int,
    private val movieRepository: MovieRepository = MovieRepositoryImpl,
    private val theaterRepository: TheaterRepository = TheaterRepositoryImpl,
    private val reservationMovieInfoRepository: ReservationMovieInfoRepository =
        ReservationMovieInfoRepositoryImpl,
) : MovieDetailContract.Presenter {
    private var view: MovieDetailContract.View? = null
    private val movie: Movie = movieRepository.findMovieById(movieId)
    private val reservationMovieInfo: ReservationMovieInfo by lazy {
        setScreeningMovieInfo()
    }
    private val reservationCount: ReservationCount = ReservationCount()
    private val movieUiModel: MovieUiModel = MovieUiModel(movie)

    private fun setScreeningMovieInfo(): ReservationMovieInfo {
        val theaterName = theaterRepository.theaterName(theaterId)
        return ReservationMovieInfo(movie.title, theaterName, movie.screeningInfo)
    }

    override fun attachView(view: MovieDetailContract.View) {
        this.view = view
        onViewSetUp()
    }

    override fun detachView() {
        this.view = null
    }

    override fun onViewSetUp() {
        loadMovieDetails()
        loadScreeningDates()
    }

    override fun loadMovieDetails() {
        view?.showMovieDetails(movieUiModel)
    }

    override fun loadScreeningDates() {
        val dates = theaterRepository.getScreeningDateInfo(theaterId, movieId)
        val times = theaterRepository.getScreeningTimeInfo(theaterId, movieId, dates[DEFAULT_DATA_INDEX])
        Log.d("in loadScreeningDates", "times: $times")
        view?.setScreeningDates(dates, DEFAULT_DATA_INDEX)
    }

    override fun loadScreeningTimes(date: String) {
        Log.d("selected date", date)
        val times = theaterRepository.getScreeningTimeInfo(theaterId, movieId, date)
        Log.d("in loadScreeningTimes", "times: $times")
        view?.updateScreeningTimes(times, DEFAULT_DATA_INDEX)
    }

    override fun selectDate(date: String) {
        val screeningDate = LocalDate.parse(date, DEFAULT_DATE_FORMAT)
        reservationMovieInfo.changeDate(
            year = screeningDate.year,
            month = screeningDate.monthValue,
            day = screeningDate.dayOfMonth,
        )
        loadScreeningTimes(date)
    }

    override fun selectTime(time: String) {
        val screeningTime = LocalTime.parse(time)
        reservationMovieInfo.changeTime(
            hour = screeningTime.hour,
            minute = screeningTime.minute,
        )
    }

    override fun minusReservationCount() {
        reservationCount.minusCount()
        view?.updateReservationCount(reservationCount.count)
    }

    override fun plusReservationCount() {
        reservationCount.plusCount()
        view?.updateReservationCount(reservationCount.count)
    }

    override fun initReservationCount(count: Int) {
        reservationCount.initCount(count)
        view?.updateReservationCount(reservationCount.count)
    }

    override fun onReserveButtonClicked() {
        reservationMovieInfoRepository.saveMovieInfo(reservationMovieInfo)
        view?.moveToSeatSelection(reservationCount.count, reservationMovieInfo.title)
    }

    companion object {
        val DEFAULT_DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        const val DEFAULT_TIME_FORMAT = "HH:mm"
        const val DEFAULT_DATA_INDEX = 0
    }
}

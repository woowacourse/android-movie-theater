package woowacourse.movie.ui.booking.presenter

import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TheaterSchedules
import woowacourse.movie.sample.DUMMY_MOVIES
import woowacourse.movie.sample.DUMMY_THEATERS
import woowacourse.movie.ui.booking.contract.BookingContract
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val bookingView: BookingContract.View,
) : BookingContract.Presenter {
    private var _headcount: Headcount = Headcount()
    val headcount get() = _headcount

    private lateinit var theater: Theater
    private lateinit var theaterSchedules: TheaterSchedules
    private lateinit var movie: Movie

    private lateinit var spinnerDates: List<LocalDate>
    private lateinit var spinnerTimes: List<LocalTime>
    private var selectedDatePosition: Int = 0
    private var selectedTimePosition: Int = 0

    override fun loadBookingInfos(
        theater: Theater?,
        movieId: Long,
    ) {
        this.theater = theater ?: DUMMY_THEATERS.theaters.first()
        this.theaterSchedules = this.theater.theaterSchedules
        this.movie = DUMMY_MOVIES[movieId] ?: DUMMY_MOVIES[0]!!
        spinnerDates = theaterSchedules.screeningDates(movieId)
        spinnerTimes = theaterSchedules.screeningTimes(movieId, LocalDateTime.now())

        bookingView.showMovie(movie)
        bookingView.showHeadCount(headcount)
        bookingView.displayScreeningDateSpinner(spinnerDates)
        bookingView.displayScreeningTimeSpinner(spinnerTimes)
    }

    override fun updateScreeningDate(screeningDate: LocalDate) {
        val position = spinnerDates.indexOf(screeningDate)
        selectedDatePosition = position
        bookingView.showScreeningDate(position)
        updateScreeningTimeSpinner()
    }

    override fun updateScreeningTime(screeningTime: LocalTime) {
        val position = spinnerTimes.indexOf(screeningTime)
        selectedTimePosition = position
        bookingView.showScreeningTime(position)
    }

    override fun updateScreeningTimeSpinner() {
        val selectedDate = spinnerDates[selectedDatePosition]
        val dateTime = LocalDateTime.of(selectedDate, LocalTime.now())

        spinnerTimes = theaterSchedules.screeningTimes(movie.id, dateTime)
        bookingView.displayScreeningTimeSpinnerItems(spinnerTimes)

        // configuration change 문제 조금 더 해결 필요 ..
        if (spinnerTimes.size <= selectedTimePosition) selectedTimePosition = 0

        bookingView.showScreeningTime(selectedTimePosition)
    }

    override fun increaseHeadcount() {
        _headcount.increase()
        bookingView.showHeadCount(headcount)
    }

    override fun decreaseHeadcount() {
        _headcount.decrease()
        bookingView.showHeadCount(headcount)
    }

    override fun restoreBookingInfos(
        count: Int,
        selectedDatePosition: Int,
        selectedTimePosition: Int,
    ) {
        _headcount = Headcount(count)
        bookingView.showHeadCount(headcount)

        this.selectedDatePosition = selectedDatePosition
        val date = spinnerDates[selectedDatePosition]
        updateScreeningDate(date)

        spinnerTimes = theaterSchedules.screeningTimes(movie.id, LocalDateTime.of(date, LocalTime.now()))
        this.selectedTimePosition = selectedTimePosition
    }

    override fun completeBooking() {
        val date = spinnerDates[selectedDatePosition]
        val time = spinnerTimes[selectedTimePosition]
        val schedule =
            theaterSchedules.movieScheduleByMovieIdAndDateTime(
                movie.id,
                LocalDateTime.of(date, time),
            )!!
        bookingView.moveToSelectSeat(
            movieId = movie.id,
            movieSchedule = schedule,
            headcount = headcount,
            theaterName = theater.name,
        )
    }
}

package woowacourse.movie.ui.booking.presenter

import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.theater.Schedule
import woowacourse.movie.domain.model.theater.ScreeningTimeSchedule
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.ui.booking.contract.BookingContract
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val bookingView: BookingContract.View,
) : BookingContract.Presenter {
    private lateinit var headcount: Headcount
    private lateinit var availableTheater: Theater
    private lateinit var movie: Movie
    private var selectedDatePosition: Int = 0
    private var selectedTimePosition: Int = 0

    private var selectedDate: LocalDate = LocalDate.MIN
    private lateinit var selectedDateTime: LocalDateTime

    fun updateViews() {
        refreshMovieInfo()
        setupDateSpinner()
    }

    override fun loadState(
        theater: Theater,
        headcount: Headcount,
        movie: Movie,
        selectedDatePosition: Int,
        selectedTimePosition: Int,
    ) {
        this.availableTheater = theater
        this.headcount = headcount
        this.movie = movie
        this.selectedDatePosition = selectedDatePosition
        this.selectedTimePosition = selectedTimePosition
    }

    override fun loadSelectedDate(
        selectedDate: LocalDate,
        selectedDatePosition: Int,
    ) {
        this.selectedDate = selectedDate
        this.selectedDatePosition = selectedDatePosition
    }

    override fun loadSelectedTime(selectedTimePosition: Int) {
        this.selectedTimePosition = selectedTimePosition
    }

    override fun loadSelectedDateTime(selectedDateTime: LocalDateTime) {
        this.selectedDateTime = selectedDateTime
    }

    override fun increaseHeadcount() {
        headcount.increase()
        bookingView.updateHeadcountDisplay(headcount)
    }

    override fun decreaseHeadcount() {
        headcount.decrease()
        bookingView.updateHeadcountDisplay(headcount)
    }

    override fun refreshMovieInfo() {
        bookingView.setMovieInfoViews(movie)
    }

    override fun refreshHeadcountDisplay() {
        bookingView.updateHeadcountDisplay(headcount)
    }

    override fun setupDateSpinner() {
        val schedules: List<Schedule> = availableTheater.allSchedules[movie] ?: emptyList()
        val screeningTimeSchedules: List<Schedule> =
            schedules.mapNotNull { it.bookableSchedule(movie, LocalDateTime.now()) }
        val screeningDates: List<LocalDate> =
            screeningTimeSchedules.map { it.screeningTimeSchedule.date }

        bookingView.setDateSpinner(screeningDates, selectedDatePosition)
    }

    override fun setupTimeSpinner() {
        val schedules: List<Schedule> = availableTheater.allSchedules[movie] ?: emptyList()
        val screeningSchedules: List<Schedule> =
            schedules.mapNotNull { it.bookableSchedule(movie, LocalDateTime.now()) }
        val screeningTimeSchedules: List<ScreeningTimeSchedule> =
            screeningSchedules.map { it.screeningTimeSchedule }
        val filteredScreeningTimeSchedules: ScreeningTimeSchedule? =
            screeningTimeSchedules.find { it.date == selectedDate }
        val screeningTimes: List<LocalTime> = filteredScreeningTimeSchedules?.time ?: emptyList()

        bookingView.setTimeSpinner(screeningTimes, selectedTimePosition)
    }

    override fun completeBooking() {
        bookingView.startBookingSeatActivity(
            movie.title,
            selectedDateTime,
            headcount,
            availableTheater,
        )
    }
}

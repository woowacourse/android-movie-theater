package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.movie.MovieScheduler
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
) : BookingContract.Presenter {
    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null
    private var headCount: HeadCount = HeadCount.of()
    private lateinit var screeningInfo: ScreeningInfo
    private lateinit var movieScheduler: MovieScheduler

    override fun initializeBooking(screeningInfo: ScreeningInfo) {
        this.screeningInfo = screeningInfo
        movieScheduler = MovieScheduler(screeningInfo.movie.startScreeningDate, screeningInfo.movie.endScreeningDate)
        view.initBooking()
        view.showMovie(screeningInfo.movie)
        view.showBookableDates(movieScheduler.getBookableDates())
        view.updateHeadCount(headCount.value)
    }

    override fun selectDate(selectedDate: LocalDate) {
        this.selectedDate = selectedDate
        view.showBookableTimes(
            movieScheduler.getBookableTimes(
                selectedDate,
                screeningTimes = screeningInfo.times,
            ),
        )
    }

    override fun selectTime(selectedTime: LocalTime) {
        this.selectedTime = selectedTime
    }

    override fun increaseHeadCount() {
        headCount = headCount.increase()
        view.updateHeadCount(headCount.value)
    }

    override fun decreaseHeadCount() {
        headCount = headCount.decrease()
        view.updateHeadCount(headCount.value)
    }

    override fun confirmBooking() {
        val ticket =
            MovieTicket(
                movieTitle = screeningInfo.movie.title,
                theaterName = screeningInfo.theater,
                screeningDateTime = LocalDateTime.of(selectedDate, selectedTime),
                headCount = headCount.value,
            )
        view.navigateToSeats(ticket)
    }

    override fun restoreBookingState(
        count: Int?,
        date: LocalDate?,
        time: LocalTime?,
    ) {
        count?.let { headCount = HeadCount.of(it) }
        selectedDate = date
        selectedTime = time
        view.updateHeadCount(headCount.value)
    }
}

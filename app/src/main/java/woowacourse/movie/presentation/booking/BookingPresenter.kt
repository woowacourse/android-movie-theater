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
    private val screeningInfo: ScreeningInfo,
) : BookingContract.Presenter {
    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null
    private var headCount: HeadCount = HeadCount()
    private val movieScheduler: MovieScheduler by lazy {
        MovieScheduler(
            screeningInfo.movie.startScreeningDate,
            screeningInfo.movie.endScreeningDate,
        )
    }

    override fun onViewCreated() {
        view.initBooking()
        view.showMovie(screeningInfo.movie)
        view.showBookableDates(movieScheduler.getBookableDates())
        view.updateHeadCount(headCount.value)
    }

    override fun onDateSelected(selectedDate: LocalDate) {
        this.selectedDate = selectedDate
        view.showBookableTimes(
            movieScheduler.getBookableTimes(
                selectedDate,
                screeningTimes = screeningInfo.times,
            ),
        )
    }

    override fun onTimeSelected(selectedTime: LocalTime) {
        this.selectedTime = selectedTime
    }

    override fun onIncreaseHeadCount() {
        headCount.increase()
        view.updateHeadCount(headCount.value)
    }

    override fun onDecreaseHeadCount() {
        headCount.decrease()
        view.updateHeadCount(headCount.value)
    }

    override fun onConfirmClicked() {
        val ticket =
            MovieTicket(
                movieTitle = screeningInfo.movie.title,
                theaterName = screeningInfo.theater,
                screeningDateTime = LocalDateTime.of(selectedDate, selectedTime),
                headCount = headCount.value,
            )
        view.navigateToSeats(ticket)
    }

    override fun onConfigurationChanged(
        count: Int?,
        date: LocalDate?,
        time: LocalTime?,
    ) {
        count?.let { headCount = HeadCount(it) }
        selectedDate = date
        selectedTime = time
        view.updateHeadCount(headCount.value)
    }
}

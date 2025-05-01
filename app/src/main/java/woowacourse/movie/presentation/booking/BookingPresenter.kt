package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.movie.MovieScheduler
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    private val screening: Screening,
) : BookingContract.Presenter {
    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null
    private var headCount: HeadCount = HeadCount()
    private val movieScheduler: MovieScheduler by lazy {
        MovieScheduler(
            screening.movie.startScreeningDate,
            screening.movie.endScreeningDate,
        )
    }

    override fun onViewCreated() {
        view.initBooking()
        view.showMovie(screening.movie)
        view.showBookableDates(movieScheduler.getBookableDates())
        view.updateHeadCount(headCount.value)
    }

    override fun onDateSelected(selectedDate: LocalDate) {
        this.selectedDate = selectedDate
        view.showBookableTimes(
            movieScheduler.getBookableTimes(
                selectedDate,
                screeningTimes = screening.times,
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
                movieTitle = screening.movie.title,
                theaterName = screening.theater,
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

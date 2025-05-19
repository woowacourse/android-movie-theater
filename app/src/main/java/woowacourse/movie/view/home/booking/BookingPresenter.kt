package woowacourse.movie.view.home.booking

import woowacourse.movie.data.dummy.MovieStore
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.Schedule
import woowacourse.movie.domain.model.feed.Feed.Movie
import woowacourse.movie.view.home.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    private val screeningInfo: ScreeningInfo,
) : BookingContract.Presenter {
    lateinit var booking: Booking
    private val schedule = Schedule(screeningInfo.screenings)

    override fun loadBooking(now: LocalDateTime) {
        val movie = MovieStore().movies[screeningInfo.movieId]
        val initialDate: LocalDate =
            loadBookableDates(now).firstOrNull() ?: run {
                view.notifyNoAvailableTime()
                return
            }
        val initialTime: LocalTime =
            loadBookableTimes(initialDate, now).firstOrNull() ?: run {
                view.notifyNoAvailableTime()
                return
            }
        initBooking(movie, initialDate, initialTime)

        view.showMovieDetail(movie)
        view.showAdmissionCount(booking.count.value)
    }

    private fun loadBookableDates(now: LocalDateTime): List<LocalDate> {
        val bookableDates = schedule.bookableDates(now)
        if (bookableDates.isNotEmpty()) view.showScreeningDates(bookableDates)
        return bookableDates
    }

    private fun loadBookableTimes(
        initialDate: LocalDate,
        now: LocalDateTime,
    ): List<LocalTime> {
        val bookableTimes = this.schedule.bookableTimes(initialDate, now)
        if (bookableTimes.isNotEmpty()) {
            view.showScreeningTimes(bookableTimes, bookableTimes.first())
        }
        return bookableTimes
    }

    private fun initBooking(
        movie: Movie,
        initialDate: LocalDate,
        initialTime: LocalTime,
    ) {
        booking =
            Booking(
                movie.title,
                screeningInfo.theaterName,
                initialDate,
                initialTime,
                AdmissionCount(),
            )
    }

    override fun restoreBooking(booking: Booking) {
        this.booking = booking
        view.showAdmissionCount(booking.count.value)
    }

    override fun completeBooking() {
        view.moveToBookingComplete(booking)
    }

    override fun selectDate(date: LocalDate) {
        val bookableTimes = schedule.bookableTimes(date, LocalDateTime.now())
        if (bookableTimes.isEmpty()) {
            view.notifyNoAvailableTime()
        } else {
            view.showScreeningTimes(bookableTimes, booking.screeningTime)
        }
        booking = booking.copy(screeningDate = date)
    }

    override fun selectTime(time: LocalTime) {
        booking = booking.copy(screeningTime = time)
    }

    override fun decreaseAdmissionCount() {
        booking = booking.copy(count = booking.count.decrease())
        view.showAdmissionCount(booking.count.value)
    }

    override fun increaseAdmissionCount(limit: Int) {
        booking = booking.copy(count = booking.count.increase(limit))
        view.showAdmissionCount(booking.count.value)
    }
}

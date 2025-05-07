package woowacourse.movie.view.home.booking

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.ScreeningDates
import woowacourse.movie.domain.model.booking.ScreeningTimes
import woowacourse.movie.view.home.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    private val screeningInfo: ScreeningInfo,
) : BookingContract.Presenter {
    lateinit var booking: Booking

    override fun initBooking(now: LocalDateTime) {
        val movie = MovieStore().movies[screeningInfo.movieId]
        val screenings = screeningInfo.screenings

        val screeningDates = ScreeningDates(screenings.map { screening -> screening.toLocalDate() })
        val bookableDates: List<LocalDate> = screeningDates.bookableDates(now.toLocalDate())
        if (bookableDates.isEmpty()) {
            view.notifyNoAvailableTime()
            return
        }
        val defaultDate: LocalDate = bookableDates.first()

        val screeningTimes = ScreeningTimes(now, screeningInfo.screeningTimes(defaultDate))
        val bookableTimes: List<LocalTime> = screeningTimes.bookableTimes(defaultDate)
        if (bookableTimes.isEmpty()) {
            view.notifyNoAvailableTime()
            return
        }
        val defaultTime: LocalTime = bookableTimes.first()

        booking =
            Booking(
                movie.title,
                screeningInfo.theaterName,
                defaultDate,
                defaultTime,
                AdmissionCount(),
            )

        view.showMovieDetail(movie, screenings)
        view.showScreeningPeriod(screeningDates.startDate, screeningDates.endDate)
        view.showScreeningDates(bookableDates)
        view.showScreeningTimes(bookableTimes, defaultTime)
        view.showAdmissionCount(booking.count.value)
    }

    override fun loadBooking(booking: Booking) {
        this.booking = booking
        view.showAdmissionCount(booking.count.value)
    }

    override fun completeBooking() {
        view.moveToBookingComplete(booking)
    }

    override fun selectDate(date: LocalDate) {
        val timesOnSelectedDate = screeningInfo.screeningTimes(date)
        val bookableTimes =
            ScreeningTimes(LocalDateTime.now(), timesOnSelectedDate).bookableTimes(date)

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

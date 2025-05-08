package woowacourse.movie.view.home.booking

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.Schedule
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

    override fun initBooking(now: LocalDateTime) {
        val movie = MovieStore().movies[screeningInfo.movieId]
        val screenings = screeningInfo.screenings

        val bookableDates = this.schedule.bookableDates(now)
        if (bookableDates.isEmpty()) {
            view.notifyNoAvailableTime()
            return
        }
        val defaultDate: LocalDate =
            bookableDates.firstOrNull() ?: run {
                view.notifyNoAvailableTime()
                return
            }
        val bookableTimes = this.schedule.bookableTimes(defaultDate, now)
        val defaultTime: LocalTime =
            bookableTimes.firstOrNull() ?: run {
                view.notifyNoAvailableTime()
                return
            }

        booking =
            Booking(
                movie.title,
                screeningInfo.theaterName,
                defaultDate,
                defaultTime,
                AdmissionCount(),
            )

        view.showMovieDetail(movie, screenings)
        view.showScreeningPeriod(this.schedule.startDate, this.schedule.endDate)
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

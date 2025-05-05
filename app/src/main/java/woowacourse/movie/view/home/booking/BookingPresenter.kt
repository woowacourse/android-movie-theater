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
    private val movies: MovieStore,
    private var count: AdmissionCount,
    private val screeningInfo: ScreeningInfo,
) : BookingContract.Presenter {
    init {
        loadAdmissionCount()
    }

    override fun loadAdmissionCount() {
        view.showAdmissionCount(count.value)
    }

    override fun loadMovieDetail() {
        val screenings = screeningInfo.screenings
        val screeningDates = screenings.map { screening -> screening.toLocalDate() }
        val screeningBookingDates = ScreeningDates(screeningDates)

        loadScreeningDates(screenings, LocalDateTime.now())
        view.showScreeningPeriod(screeningBookingDates.startDate, screeningBookingDates.endDate)
        view.showMovieDetail(movies.movies[screeningInfo.movieId], screenings)
    }

    override fun loadScreeningDates(
        screeningDateTimes: List<LocalDateTime>,
        now: LocalDateTime,
    ) {
        val screeningDates = screeningDateTimes.map { dateTime -> dateTime.toLocalDate() }
        val bookableDates: List<LocalDate> = ScreeningDates(screeningDates).bookableDates(now.toLocalDate())
        view.showScreeningDates(bookableDates)
    }

    override fun loadScreeningTimes(
        selectedDate: LocalDate,
        now: LocalDateTime,
    ) {
        val timeOnSelectedDate = screeningInfo.screeningTimes(selectedDate)
        val bookableTimes = ScreeningTimes(now, timeOnSelectedDate).bookableTimes(selectedDate)

        if (bookableTimes.isEmpty()) {
            view.notifyNoAvailableTime()
        } else {
            view.showScreeningTimes(bookableTimes)
        }
    }

    override fun loadBooking(
        movieTitle: String,
        screeningDate: String,
        screeningTime: String,
        admissionCount: String,
    ) {
        val booking =
            Booking(
                movieTitle = movieTitle,
                theaterName = screeningInfo.theaterName,
                screeningDate = LocalDate.parse(screeningDate),
                screeningTime = LocalTime.parse(screeningTime),
                count = AdmissionCount(admissionCount.toInt()),
            )

        view.moveToBookingComplete(booking)
    }

    override fun decreaseAdmissionCount() {
        count = count.decrease()
        view.showAdmissionCount(count.value)
    }

    override fun increaseAdmissionCount(limit: Int) {
        count = count.increase(limit)
        view.showAdmissionCount(count.value)
    }

    override fun restoreAdmissionCount(savedCount: Int) {
        count = AdmissionCount(savedCount)
        view.showAdmissionCount(count.value)
    }
}

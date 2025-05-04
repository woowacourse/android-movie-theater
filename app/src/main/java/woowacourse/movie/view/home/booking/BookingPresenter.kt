package woowacourse.movie.view.home.booking

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.PeopleCount
import woowacourse.movie.domain.model.booking.ScreeningDate
import woowacourse.movie.domain.model.booking.ScreeningTime
import woowacourse.movie.view.home.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    private val movies: MovieStore,
    private var count: PeopleCount,
    private val screeningInfo: ScreeningInfo,
) : BookingContract.Presenter {
    init {
        loadPeopleCount()
    }

    override fun loadMovieDetail() {
        val screening = screeningInfo.screening
        val screeningDate = screening.map { it.toLocalDate() }

        val screeningBookingDates = ScreeningDate(screeningDate)

        loadScreeningDate(screeningInfo.screening, LocalDateTime.now())

        view.showScreeningPeriod(screeningBookingDates.startDate, screeningBookingDates.endDate)
        view.showMovieDetail(movies[screeningInfo.movieId], screening)
    }

    override fun loadPeopleCount() {
        view.showPeopleCount(count.value)
    }

    override fun loadScreeningDate(
        screeningDateTime: List<LocalDateTime>,
        now: LocalDateTime,
    ) {
        val screeningDate = screeningDateTime.map { it.toLocalDate() }

        val screeningBookingDates: List<LocalDate> =
            ScreeningDate(screeningDate)
                .bookingDates(now.toLocalDate())

        view.showScreeningDate(screeningBookingDates)
    }

    override fun loadScreeningTime(
        selectedDate: LocalDate,
        now: LocalDateTime,
    ) {
        val timeOnSelectedDate = screeningInfo.screeningTime(selectedDate)
        val availableTimes =
            ScreeningTime(now, timeOnSelectedDate)
                .getAvailableScreeningTimes(selectedDate)

        if (availableTimes.isEmpty()) {
            view.showToast()
            return
        }
        view.showScreeningTime(availableTimes)
    }

    override fun decreasePeopleCount() {
        count = count.decrease()
        view.showPeopleCount(count.value)
    }

    override fun increasePeopleCount(limit: Int) {
        count = count.increase(limit)
        view.showPeopleCount(count.value)
    }

    override fun restorePeopleCount(savedCount: Int) {
        count = PeopleCount(savedCount)
        view.showPeopleCount(count.value)
    }

    override fun loadBooking(
        title: String,
        bookingDate: String,
        bookingTime: String,
        count: String,
    ) {
        val booking =
            Booking(
                title = title,
                theaterName = screeningInfo.theaterName,
                bookingDate = LocalDate.parse(bookingDate),
                bookingTime = LocalTime.parse(bookingTime),
                count = PeopleCount(count.toInt()),
            )

        view.moveToBookingComplete(booking)
    }
}

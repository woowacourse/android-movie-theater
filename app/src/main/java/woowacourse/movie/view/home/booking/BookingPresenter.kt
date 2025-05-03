package woowacourse.movie.view.home.booking

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.PeopleCount
import woowacourse.movie.domain.model.booking.ScreeningDate
import woowacourse.movie.domain.model.booking.ScreeningTime
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.mapper.toUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter private constructor(
    private val view: BookingContract.View,
    private val movies: MovieStore,
    private var count: PeopleCount,
    private val screeningInfo: ScreeningInfo,
    private val initialTime: LocalDateTime = LocalDateTime.now(),
) : BookingContract.Presenter {
    init {
        loadPeopleCount()
    }

    override fun loadMovieDetail() {
        view.showMovieDetail(movies[screeningInfo.movieId].toUiModel(), screeningInfo.screening)
        loadScreening()
    }

    override fun loadPeopleCount() {
        view.showPeopleCount(count.value)
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
            return view.guideNoBookingTime()
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

    private fun loadScreening() {
        val screeningDate = screeningInfo.screening.map { it.toLocalDate() }

        val screeningBookingDates: List<LocalDate> =
            ScreeningDate(screeningDate)
                .bookingDates(initialTime.toLocalDate())

        view.showScreeningDate(screeningBookingDates)
        loadScreeningTime(screeningBookingDates.first(), initialTime)
    }

    companion object {
        fun initialize(
            view: BookingContract.View,
            screeningInfo: ScreeningInfo,
        ): BookingPresenter {
            return BookingPresenter(
                view,
                MovieStore(),
                PeopleCount(),
                screeningInfo,
            )
        }
    }
}

package woowacourse.movie.view.home.booking

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.ScreeningDate
import woowacourse.movie.domain.model.booking.ScreeningTime
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenter(
    private val view: BookingContract.View,
    private val movies: MovieStore,
    private val screeningInfo: ScreeningInfo,
    private val initialTime: LocalDateTime = LocalDateTime.now(),
) : BookingContract.Presenter {
    private var booking = Booking.initialize(screeningInfo.theaterName)
    private val screeningDate =
        ScreeningDate(
            screeningInfo.screeningDate(),
        ).bookingDates(initialTime.toLocalDate())

    private var availableScreeningTimes: List<LocalTime> = emptyList()

    override fun loadMovieDetail() {
        val movie = movies[screeningInfo.movieId]
        booking = booking.modifyMovieTitle(movie.title)
        view.showMovieDetail(movie, screeningInfo.screening)
        loadScreening()
        loadPeopleCount()
    }

    override fun loadPeopleCount() {
        view.showPeopleCount(booking.count.value)
    }

    override fun loadScreeningTime(
        selectedDate: LocalDate,
        now: LocalDateTime,
    ) {
        val timeOnSelectedDate = screeningInfo.screeningTime(selectedDate)
        availableScreeningTimes =
            ScreeningTime(now, timeOnSelectedDate)
                .getAvailableScreeningTimes(selectedDate)

        if (availableScreeningTimes.isEmpty()) {
            return view.guideNoBookingTime()
        }
        booking = booking.modifyBookingTime(availableScreeningTimes.first())
        view.showScreeningTime(availableScreeningTimes)
    }

    override fun decreasePeopleCount() {
        booking = booking.decreasePeopleCount()
        loadPeopleCount()
    }

    override fun increasePeopleCount(limit: Int) {
        booking = booking.increasePeopleCount(limit)
        loadPeopleCount()
    }

    override fun restoreSavedData(
        savedDate: Int,
        savedTime: Int,
        savedCount: Int,
    ) {
        val selectedDate = screeningDate[savedDate]
        val selectedTime = availableScreeningTimes[savedTime]

        booking =
            booking.restore(
                selectedDate,
                selectedTime,
                savedCount,
            )

        loadPeopleCount()
    }

    override fun loadBooking() {
        view.moveToBookingComplete(booking)
    }

    private fun loadScreening() {
        val startDate = screeningDate.first()

        view.showScreeningDate(screeningDate)
        booking = booking.modifyBookingDate(startDate)

        loadScreeningTime(startDate, initialTime)
    }

    companion object {
        fun initialize(
            view: BookingContract.View,
            screeningInfo: ScreeningInfo,
        ): BookingPresenter {
            val movieStore = MovieStore()

            return BookingPresenter(
                view,
                movieStore,
                screeningInfo,
            )
        }
    }
}

package woowacourse.movie.view.home.booking

import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.movies.Movie
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {
    interface View {
        fun showMovieDetail(
            movie: Movie,
            screeningTimes: List<LocalDateTime>,
        )

        fun showAdmissionCount(count: Int)

        fun showScreeningPeriod(
            startDate: LocalDate,
            endDate: LocalDate,
        )

        fun showScreeningDates(bookableDates: List<LocalDate>)

        fun showScreeningTimes(bookableTimes: List<LocalTime>)

        fun notifyNoAvailableTime()

        fun moveToBookingComplete(booking: Booking)
    }

    interface Presenter {
        fun loadAdmissionCount()

        fun loadMovieDetail()

        fun loadScreeningDates(
            screeningDateTimes: List<LocalDateTime>,
            now: LocalDateTime,
        )

        fun loadScreeningTimes(
            selectedDate: LocalDate,
            now: LocalDateTime,
        )

        fun loadBooking(
            movieTitle: String,
            screeningDate: String,
            screeningTime: String,
            admissionCount: String,
        )

        fun decreaseAdmissionCount()

        fun increaseAdmissionCount(limit: Int)

        fun restoreAdmissionCount(savedCount: Int)
    }
}

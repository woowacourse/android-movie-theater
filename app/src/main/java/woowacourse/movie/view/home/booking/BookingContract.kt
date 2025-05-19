package woowacourse.movie.view.home.booking

import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.feed.Feed.Movie
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {
    interface View {
        fun showMovieDetail(movie: Movie)

        fun showAdmissionCount(count: Int)

        fun showScreeningDates(bookableDates: List<LocalDate>)

        fun showScreeningTimes(
            bookableTimes: List<LocalTime>,
            savedTime: LocalTime,
        )

        fun notifyNoAvailableTime()

        fun moveToBookingComplete(booking: Booking)
    }

    interface Presenter {
        fun loadBooking(now: LocalDateTime)

        fun restoreBooking(booking: Booking)

        fun selectDate(date: LocalDate)

        fun selectTime(time: LocalTime)

        fun decreaseAdmissionCount()

        fun increaseAdmissionCount(limit: Int)

        fun completeBooking()
    }
}

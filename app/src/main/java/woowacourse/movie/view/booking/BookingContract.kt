package woowacourse.movie.view.booking

import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Movie
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {
    interface View {
        fun showMovieDetail(
            movie: Movie,
            screeningTimes: List<LocalDateTime>,
        )

        fun showPeopleCount(count: Int)

        fun showScreeningDate(screeningBookingDates: List<LocalDate>)

        fun showScreeningTime(screeningBookingTimes: List<LocalTime>)

        fun guideNoBookingTime()

        fun moveToBookingComplete(booking: Booking)
    }

    interface Presenter {
        fun loadMovieDetail()

        fun loadPeopleCount()

        fun loadScreeningTime(
            selectedDate: LocalDate,
            now: LocalDateTime,
        )

        fun loadBooking()

        fun decreasePeopleCount()

        fun increasePeopleCount(limit: Int)

        fun restoreSavedData(
            savedDate: Int,
            savedTime: Int,
            savedCount: Int,
        )
    }
}

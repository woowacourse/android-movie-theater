package woowacourse.movie.view.home.booking

import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.view.uiModel.MovieUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {
    interface View {
        fun showMovieDetail(
            movie: MovieUiModel,
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

        fun loadBooking(
            title: String,
            bookingDate: String,
            bookingTime: String,
            peopleCount: String,
        )

        fun decreasePeopleCount()

        fun increasePeopleCount(limit: Int)

        fun restorePeopleCount(savedCount: Int)
    }
}

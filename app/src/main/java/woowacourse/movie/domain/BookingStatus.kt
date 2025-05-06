package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.seat.BookingSeats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class BookingStatus(
    val movie: Movie,
    val isBooked: Boolean = true,
    val seat: BookingSeats,
    val bookedTime: LocalDateTime,
) : Parcelable {
    val memberCount: Int
        get() = seat.value

    fun calculateTicketPrices(): Int {
        return seat.calculateTicketPrices()
    }

    fun book(): BookingResult {
        return if (!isBooked) {
            BookingResult.Success(this.copy(isBooked = true))
        } else {
            BookingResult.AlreadyBooked
        }
    }

    fun cancel(): BookingResult {
        return if (isBooked) {
            BookingResult.Success(this.copy(isBooked = false))
        } else {
            BookingResult.NotBook
        }
    }

    companion object {
        private fun from(
            movie: Movie,
            count: Int,
            bookedDate: LocalDate,
            bookedTime: LocalTime,
        ): BookingStatus {
            val bookedDateTime = LocalDateTime.of(bookedDate, bookedTime)
            return BookingStatus(
                movie = movie,
                seat = BookingSeats(count),
                bookedTime = bookedDateTime,
            )
        }

        operator fun invoke(
            movie: Movie,
            count: Int,
            bookedDate: LocalDate,
            bookedTime: LocalTime,
        ): BookingStatus = from(movie, count, bookedDate, bookedTime)
    }
}

sealed class BookingResult {
    data class Success(val status: BookingStatus) : BookingResult()
    object AlreadyBooked : BookingResult()
    object NotBook : BookingResult()
}

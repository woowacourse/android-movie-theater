package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.R
import woowacourse.movie.domain.seat.BookingSeats
import woowacourse.movie.domain.seat.Seat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class BookingStatus(
    val movie: Movie,
    val isBooked: Boolean = true,
    val seat: BookingSeats,
    val bookedTime: LocalDateTime,
    val theater: Theater,
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
            theater: Theater
        ): BookingStatus {
            val bookedDateTime = LocalDateTime.of(bookedDate, bookedTime)
            return BookingStatus(
                movie = movie,
                seat = BookingSeats(count),
                bookedTime = bookedDateTime,
                theater = theater,
            )
        }

        operator fun invoke(
            movie: Movie,
            count: Int,
            bookedDate: LocalDate,
            bookedTime: LocalTime,
            theater: Theater,
        ): BookingStatus = from(movie, count, bookedDate, bookedTime, theater)

        val value = listOf(
            BookingStatus(
                Movie(
                    Title("해리포터와 마법사의 돌"),
                    R.drawable.movie_poster,
                    ScreeningPeriod.ofDot("2025.04.01", "2025.04.25"),
                    152,
                ),
                true,
                BookingSeats(2, mutableListOf(Seat.of(0, 0))),
                LocalDateTime.of(2025, 6, 5, 9, 0),
                Theater("선릉", Movies.seolleungMovies, mapOf(Title("해리포터와 마법사의 돌") to listOf(LocalTime.of(9, 0))))
            ),
            BookingStatus(
                Movie(
                    Title("해리포터와 마법사의 돌"),
                    R.drawable.movie_poster,
                    ScreeningPeriod.ofDot("2025.04.01", "2025.04.25"),
                    152,
                ),
                true,
                BookingSeats(2, mutableListOf(Seat.of(0, 0))),
                LocalDateTime.of(2025, 6, 5, 9, 0),
                Theater("선릉", Movies.seolleungMovies, mapOf(Title("해리포터와 마법사의 돌") to listOf(LocalTime.of(9, 0))))
            )
        )
    }
}

sealed class BookingResult {
    data class Success(val status: BookingStatus) : BookingResult()
    object AlreadyBooked : BookingResult()
    object NotBook : BookingResult()
}

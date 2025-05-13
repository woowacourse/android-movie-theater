package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.dao.bookingStatus.BookingStatusEntity
import woowacourse.movie.dao.bookingseats.BookingSeatEntity
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


        fun toDomain(
            bookingStatusEntity: BookingStatusEntity,
            bookingSeatEntity: List<BookingSeatEntity>,
        ): BookingStatus {
            val movie: Movie =
                (Movies.value.find(Title(bookingStatusEntity.movieTitle))
                        as? MovieResult.Success)
                    ?.movie
                    ?: throw IllegalArgumentException()
            val theater: Theater =
                (Theaters.theaters.find(bookingStatusEntity.theater)
                        as? TheaterResult.Success)
                    ?.theater
                    ?: throw IllegalArgumentException()
            val reservationDateTime = LocalDateTime.parse(bookingStatusEntity.reservationDateTime)
            val seats = BookingSeats.toDomain(bookingSeatEntity)
            return BookingStatus(
                movie = movie,
                seat = seats,
                bookedTime = reservationDateTime,
                theater = theater,
            )
        }
    }
}

sealed class BookingResult {
    data class Success(val status: BookingStatus) : BookingResult()
    object AlreadyBooked : BookingResult()
    object NotBook : BookingResult()
}

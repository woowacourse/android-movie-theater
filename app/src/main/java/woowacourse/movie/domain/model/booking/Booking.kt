package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class Booking(
    val theaterName: String,
    val movieTitle: String,
    val bookingDate: LocalDate,
    val bookingTime: LocalTime,
    val count: PeopleCount,
) : Serializable {
    fun modifyMovieTitle(newTitle: String) = this.copy(movieTitle = newTitle)

    fun modifyBookingDate(newDate: LocalDate) = this.copy(bookingDate = newDate)

    fun modifyBookingTime(newTime: LocalTime) = this.copy(bookingTime = newTime)

    fun increasePeopleCount(limit: Int) = this.copy(count = count.increase(limit))

    fun decreasePeopleCount() = this.copy(count = count.decrease())

    fun restore(
        bookingDate: LocalDate,
        bookingTime: LocalTime,
        peopleCount: Int,
    ): Booking {
        val newCount = count.modify(peopleCount)
        return this.copy(bookingDate = bookingDate, bookingTime = bookingTime, count = newCount)
    }

    companion object {
        fun initialize(theaterName: String): Booking {
            return Booking(
                movieTitle = "",
                theaterName = theaterName,
                bookingDate = LocalDate.now(),
                bookingTime = LocalTime.now(),
                count = PeopleCount(),
            )
        }
    }
}

package woowacourse.movie.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.movie.data.entity.BookingInfoEntity
import woowacourse.movie.data.entity.MovieSeatEntity
import woowacourse.movie.domain.model.BookingInfo
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieSeat
import woowacourse.movie.domain.model.MovieSeats
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.SeatType
import woowacourse.movie.domain.model.TicketCount
import java.time.LocalDate
import java.time.LocalTime

private val gson = Gson()

fun BookingInfo.toData(): BookingInfoEntity =
    BookingInfoEntity(
        movieId = movie.id,
        movieTitle = movie.title,
        startDate = movie.startDate.value.toString(),
        endDate = movie.endDate.value.toString(),
        runningTime = movie.runningTime,
        theaterName = theaterName,
        selectedDate = selectedDate.value.toString(),
        selectedTime = selectedTime.value.toString(),
        seatList = gson.toJson(selectedSeats.map { it.toData() }),
        ticketCount = currentTicketCount,
    )

fun BookingInfoEntity.toDomain(): BookingInfo {
    val seatType = object : TypeToken<List<MovieSeatEntity>>() {}.type
    val dataSeats: List<MovieSeatEntity> = gson.fromJson(seatList, seatType)
    return BookingInfo(
        movie =
            Movie(
                id = movieId,
                title = movieTitle,
                startDate = MovieDate(LocalDate.parse(startDate)),
                endDate = MovieDate(LocalDate.parse(endDate)),
                runningTime = runningTime,
            ),
        theaterName = theaterName,
        date = MovieDate(LocalDate.parse(selectedDate)),
        time = MovieTime(LocalTime.parse(selectedTime)),
        seats = MovieSeats(dataSeats.map { it.toDomain() }.toSet()),
        ticketCount = TicketCount(ticketCount),
    )
}

fun MovieSeat.toData(): MovieSeatEntity = MovieSeatEntity(row, column, seatType.name, isSelected)

fun MovieSeatEntity.toDomain(): MovieSeat = MovieSeat(row, column, SeatType.valueOf(seatType), isSelected)

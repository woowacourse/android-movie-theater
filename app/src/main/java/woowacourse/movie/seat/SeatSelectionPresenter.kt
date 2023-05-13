package woowacourse.movie.seat

import domain.Seat
import domain.Seats
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.dto.movie.SeatMovieDto
import woowacourse.movie.dto.seat.SeatsDto
import woowacourse.movie.mapper.seat.mapToSeats
import woowacourse.movie.mapper.seat.mapToSeatsDto
import java.time.LocalDateTime
import java.time.ZoneId

class SeatSelectionPresenter(private val view: SeatSelectionContract.View) :
    SeatSelectionContract.Presenter {
    override lateinit var seatBaseInfo: SeatMovieDto
    override lateinit var seatsDto: SeatsDto
    override var seats = Seats()

    override fun initInfo(data: SeatMovieDto) {
        this.seatBaseInfo = data
        view.setMovieTitle(this.seatBaseInfo.movie.title)
    }

    override fun initOtherSeatState(data: SeatsDto) {
        seatsDto = data
        seats = seatsDto.mapToSeats()
        view.setPrice(
            seats.caculateSeatPrice(
                LocalDateTime.of(seatBaseInfo.movieDate.date, seatBaseInfo.movieTime.time)
            )
        )
    }

    override fun initNoneSeatState() {
        view.setPrice(0)
    }

    override fun isPossibleSelect(seat: Seat): Boolean {
        return !seats.containsSeat(seat) && seats.isPossibleSeatSize(seatBaseInfo.ticketCount.numberOfPeople)
    }

    override fun getPricePerDate() {
        view.setPrice(
            seats.caculateSeatPrice(
                LocalDateTime.of(seatBaseInfo.movieDate.date, seatBaseInfo.movieTime.time)
            )
        )
    }

    override fun getPossibleClick(): Boolean {
        return isPossibleEnter(seatBaseInfo.ticketCount.numberOfPeople)
    }

    override fun isSeatCancelable(seat: Seat): Boolean {
        return seats.containsSeat(seat)
    }

    override fun selectSeat(seat: Seat) {
        seats.add(seat)
    }

    override fun unselectSeat(seat: Seat) {
        seats.remove(seat)
    }

    override fun convertSeatsToDto(): SeatsDto {
        return seats.mapToSeatsDto()
    }

    override fun getBookingMovie(): BookingMovieDto {
        val price = seats.caculateSeatPrice(
            LocalDateTime.of(seatBaseInfo.movieDate.date, seatBaseInfo.movieTime.time)
        )

        return BookingMovieDto(
            seatBaseInfo.movie.title,
            seatBaseInfo.movieDate,
            seatBaseInfo.movieTime,
            seatBaseInfo.ticketCount,
            seats.mapToSeatsDto().getSeatsPositionToString(),
            seatBaseInfo.theaterName,
            price
        )
    }

    override fun setDateTime(): Long {
        val bookingMovie = getBookingMovie()
        val dateTime = LocalDateTime.of(bookingMovie.date.date, bookingMovie.time.time)
        return dateTime.minusMinutes(SeatSelectionActivity.ALARM_TIME.toLong())
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun isPossibleEnter(count: Int): Boolean {
        return seats.checkSeatSizeMatch(count)
    }
}

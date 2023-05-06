package woowacourse.movie.movie.seat

import android.os.Bundle
import domain.Seat
import domain.Seats
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.dto.movie.SeatMovieDto
import woowacourse.movie.movie.dto.seat.SeatsDto

interface SeatSelectionContract {
    interface View {
        val presenter: Presenter

        fun initData()
        fun setMovieTitle(title: String)
        fun setUpState(savedInstanceState: Bundle?)
        fun setPrice(ticketPrice: Int)
        fun putAlarm()
    }

    interface Presenter {
        val seatBaseInfo: SeatMovieDto
        var seats: Seats
        var seatsDto: SeatsDto

        fun initInfo(data: SeatMovieDto)
        fun initOtherSeatState(data: SeatsDto)
        fun initNoneSeatState()
        fun isPossibleSelect(seat: Seat): Boolean
        fun getPricePerDate()
        fun getPossibleClick(): Boolean
        fun isSeatCancelable(seat: Seat): Boolean
        fun selectSeat(seat: Seat)
        fun convertSeatsToDto(): SeatsDto
        fun unselectSeat(seat: Seat)
        fun getBookingMovie(): BookingMovieEntity
        fun setDateTime(): Long
    }
}

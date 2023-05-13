package woowacourse.movie.seat.view.contract

import android.os.Bundle
import domain.Seat
import domain.Seats
import woowacourse.movie.movielist.dto.SeatMovieDto
import woowacourse.movie.seat.dto.SeatsDto
import woowacourse.movie.ticket.model.BookingMovieModel

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
        fun getBookingMovie(): BookingMovieModel
        fun setDateTime(): Long
    }
}

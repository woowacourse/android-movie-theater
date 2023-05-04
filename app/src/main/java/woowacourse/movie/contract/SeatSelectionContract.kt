package woowacourse.movie.contract

import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.SeatTableViewData
import woowacourse.movie.data.SeatsViewData
import java.time.LocalDateTime

interface SeatSelectionContract {
    interface View {
        val presenter: Presenter
        fun setPriceText(price: PriceViewData)
        fun setMovieData(movie: MovieViewData)
        fun setReservationButtonState(seatsSize: Int, peopleCount: Int)
        fun makeSeatLayout(reservationDetail: ReservationDetailViewData, seatTable: SeatTableViewData)
        fun makeReservationAlarm(
            reservation: ReservationViewData,
            date: LocalDateTime
        )
        fun startReservationResultActivity(
            reservation: ReservationViewData
        )
    }

    interface Presenter {
        val view: View
        fun initActivity(movie: MovieViewData, reservationDetail: ReservationDetailViewData)
        fun selectSeat(seats: SeatsViewData, reservationDetail: ReservationDetailViewData)
        fun confirmSeats(
            movie: MovieViewData,
            reservationDetail: ReservationDetailViewData,
            seats: SeatsViewData,
            theaterName: String
        )
    }
}

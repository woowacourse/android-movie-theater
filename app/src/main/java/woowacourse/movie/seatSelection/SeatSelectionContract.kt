package woowacourse.movie.seatSelection

import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.PriceViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.model.ReservationViewData
import woowacourse.movie.common.model.SeatTableViewData
import woowacourse.movie.common.model.SeatsViewData
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
        fun selectSeat(seats: SeatsViewData, reservationDetail: ReservationDetailViewData)
        fun confirmSeats(
            movie: MovieViewData,
            reservationDetail: ReservationDetailViewData,
            seats: SeatsViewData,
            theaterName: String
        )
    }
}

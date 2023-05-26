package woowacourse.movie.activity.seatselection

import woowacourse.movie.domain.model.TicketBox
import woowacourse.movie.domain.reservationNotificationPolicy.MovieReservationNotification
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.SeatsViewData
import woowacourse.movie.view.mapper.MovieMapper.toDomain
import woowacourse.movie.view.mapper.PriceMapper.toView
import woowacourse.movie.view.mapper.ReservationDetailMapper.toDomain
import woowacourse.movie.view.mapper.ReservationMapper.toView
import woowacourse.movie.view.mapper.SeatsMapper.toDomain
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.Locale

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val reservationDataSource: woowacourse.movie.domain.dataSource.ReservationDataSource,
) : SeatSelectionContract.Presenter {
    override fun initPrice(price: PriceViewData) {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(price.value)
        view.setPriceView(SEAT_PRICE.format(formattedPrice))
    }

    override fun updateSeatsPrice(
        seats: SeatsViewData,
        reservationDetail: ReservationDetailViewData,
    ) {
        val price: PriceViewData =
            seats.toDomain().calculateDiscountedPrice(reservationDetail.toDomain()).toView()
        initPrice(price)
    }

    override fun reserveMovie(
        seats: SeatsViewData,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
    ) {
        val ticketBox = TicketBox(reservationDataSource)
        val reservation: ReservationViewData = ticketBox.ticketing(
            movie.toDomain(),
            reservationDetail.toDomain(),
            seats.toDomain(),
        ).toView()
        val alarmDate: LocalDateTime =
            MovieReservationNotification.calculateTime(reservation.reservationDetail.date)
        view.makeAlarm(alarmDate, reservation)
        view.startReservationResultActivity(reservation)
    }

    companion object {
        private const val SEAT_PRICE = "%s원"
    }
}

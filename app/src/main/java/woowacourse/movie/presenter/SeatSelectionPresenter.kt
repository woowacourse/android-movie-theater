package woowacourse.movie.presenter

import woowacourse.movie.contract.SeatSelectionContract
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.SeatTableViewData
import woowacourse.movie.data.SeatsViewData
import woowacourse.movie.data.TableSize
import woowacourse.movie.data.repository.SeatSelectionRepository
import woowacourse.movie.domain.discountPolicy.Discount
import woowacourse.movie.domain.discountPolicy.MovieDayPolicy
import woowacourse.movie.domain.discountPolicy.OffTimePolicy
import woowacourse.movie.domain.reservationNotificationPolicy.MovieReservationNotificationPolicy
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.mapper.MovieSeatMapper.toDomain
import woowacourse.movie.mapper.ReservationDetailMapper.toDomain
import woowacourse.movie.mapper.ReservationMapper.toDomain
import woowacourse.movie.mapper.SeatsMapper.toView

class SeatSelectionPresenter(
    override val view: SeatSelectionContract.View,
    val seatSelectionRepository: SeatSelectionRepository = SeatSelectionRepository()
) : SeatSelectionContract.Presenter {
    override fun initActivity(movie: MovieViewData, reservationDetail: ReservationDetailViewData) {
        view.makeSeatLayout(
            reservationDetail,
            SeatTableViewData(
                Seats.from(SEAT_ROW_COUNT, SEAT_COLUMN_COUNT).toView(),
                TableSize(SEAT_ROW_COUNT, SEAT_COLUMN_COUNT)
            )
        )
        view.setMovieData(movie)
        view.setPriceText(PriceViewData())
    }

    override fun selectSeat(seats: SeatsViewData, reservationDetail: ReservationDetailViewData) {
        view.setReservationButtonState(seats.value.size, reservationDetail.peopleCount)
        view.setPriceText(calculateDiscountedPrice(seats, reservationDetail))
    }

    override fun confirmSeats(
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
        seats: SeatsViewData
    ) {
        val price = calculateDiscountedPrice(seats, reservationDetail)

        val reservation = ReservationViewData(
            movie,
            reservationDetail,
            seats,
            price,
        )

        val domainReservation = reservation.toDomain()
        val notificationDate =
            domainReservation.calculateNotification(MovieReservationNotificationPolicy)
        view.makeReservationAlarm(reservation, notificationDate)
        seatSelectionRepository.postReservation(domainReservation)
        view.startReservationResultActivity(reservation)
    }

    private fun calculateDiscountedPrice(
        seats: SeatsViewData,
        reservationDetail: ReservationDetailViewData
    ): PriceViewData {
        val discount = Discount(listOf(MovieDayPolicy, OffTimePolicy))
        return seats.value.sumOf { seat ->
            discount.calculate(
                reservationDetail.toDomain(), seat.toDomain().row.seatRankByRow().price
            ).value
        }.let {
            PriceViewData(it)
        }
    }

    companion object {
        private const val SEAT_ROW_COUNT = 5
        private const val SEAT_COLUMN_COUNT = 4
    }
}

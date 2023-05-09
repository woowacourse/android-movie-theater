package woowacourse.movie.seatSelection

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.common.mapper.MovieSeatMapper.toDomain
import woowacourse.movie.common.mapper.ReservationDetailMapper.toDomain
import woowacourse.movie.common.mapper.ReservationMapper.toDomain
import woowacourse.movie.common.mapper.SeatsMapper.toView
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.PriceViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.model.ReservationViewData
import woowacourse.movie.common.model.SeatTableViewData
import woowacourse.movie.common.model.SeatsViewData
import woowacourse.movie.common.model.TableSize
import woowacourse.movie.common.repository.ReservationRepository
import woowacourse.movie.domain.discountPolicy.Discount
import woowacourse.movie.domain.discountPolicy.MovieDayPolicy
import woowacourse.movie.domain.discountPolicy.OffTimePolicy
import woowacourse.movie.domain.reservationNotificationPolicy.MovieReservationNotificationPolicy
import woowacourse.movie.domain.seat.Seats

class SeatSelectionPresenter(
    override val view: SeatSelectionContract.View,
    movieDao: MovieDao,
    private val reservationRepository: ReservationRepository = ReservationRepository(movieDao),
    movie: MovieViewData,
    reservationDetail: ReservationDetailViewData
) : SeatSelectionContract.Presenter {
    init {
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
        seats: SeatsViewData,
        theaterName: String
    ) {
        val price = calculateDiscountedPrice(seats, reservationDetail)

        val reservation = ReservationViewData(
            movie, reservationDetail, seats, price, theaterName
        )

        val domainReservation = reservation.toDomain()
        val notificationDate =
            domainReservation.calculateNotification(MovieReservationNotificationPolicy)
        view.makeReservationAlarm(reservation, notificationDate)
        reservationRepository.postReservation(domainReservation)
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

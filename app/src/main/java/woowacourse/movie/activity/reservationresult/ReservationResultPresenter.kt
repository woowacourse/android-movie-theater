package woowacourse.movie.activity.reservationresult

import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.SeatsViewData
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReservationResultPresenter(val view: ReservationResultContract.View) :
    ReservationResultContract.Presenter {
    override fun renderReservation(
        reservationViewData: ReservationViewData
    ) {
        val formattedDate = getFormattedDate(reservationViewData.reservationDetail.date)
        val formattedCount = getFormattedCount(
            reservationViewData.reservationDetail.peopleCount,
            reservationViewData.seats,
            reservationViewData.reservationDetail.theaterName
        )
        val formattedPrice = getFormattedPrice(reservationViewData.price)
        view.renderReservation(formattedDate, formattedCount, formattedPrice)
    }

    private fun getFormattedDate(date: LocalDateTime): String {
        val dateFormat = DateTimeFormatter.ofPattern(RESERVATION_DATETIME_FORMAT)
        return dateFormat.format(date)
    }

    private fun getFormattedCount(
        peopleCount: Int,
        seats: SeatsViewData,
        theaterName: String
    ): String = RESERVATION_PEOPLE_COUNT.format(
        peopleCount, formatSeats(seats), theaterName
    )

    private fun formatSeats(seats: SeatsViewData): String {
        return seats.seats.joinToString {
            SEAT_ROW_COLUMN.format(it.rowCharacter, it.column + 1)
        }
    }

    private fun getFormattedPrice(price: PriceViewData): String = RESERVATION_PRICE.format(
        NumberFormat.getNumberInstance(Locale.US).format(price.value)
    )

    companion object {
        private const val RESERVATION_DATETIME_FORMAT = "yyyy-MM-dd HH:mm"
        private const val RESERVATION_PEOPLE_COUNT = "일반 %d명 | %s | %s 극장"
        private const val SEAT_ROW_COLUMN = "%c%d"
        private const val RESERVATION_PRICE = "%s원 (현장 결제)"
    }
}

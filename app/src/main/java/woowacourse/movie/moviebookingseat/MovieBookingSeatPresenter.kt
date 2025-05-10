package woowacourse.movie.moviebookingseat

import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.ReservationInfo
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.helper.LocalDateHelper.toDotFormat

class MovieBookingSeatPresenter(
    private val view: MovieBookingSeat.View,
) : MovieBookingSeat.Presenter {
    private lateinit var bookingStatus: BookingStatus
    private lateinit var theater: Theater

    override fun loadBookingStatus(bookingStatus: BookingStatus, theater: Theater) {
        this.bookingStatus = bookingStatus
        this.theater = theater
        view.showBookingStatusInfo()
    }

    override fun selectSeat(seat: Seat) {
        if (seat !in bookingStatus.seat.seats && bookingStatus.seat.seats.size < bookingStatus.memberCount) {
            bookingStatus.seat.add(seat)
            view.updateSeat(seat, true)
        } else {
            bookingStatus.seat.remove(seat)
            view.updateSeat(seat, false)
        }
    }

    override fun calculatePrice() {
        val totalPrice = bookingStatus.calculateTicketPrices()
        selectedAll()
        view.showTotalPrice(totalPrice)
    }

    private fun selectedAll() {
        if (bookingStatus.seat.isSelectedAll()) view.updateButton()
    }

    override fun confirmBooking() {
        val reservationInfo =
            ReservationInfo(
                bookingStatus.movie.title,
                bookingStatus.bookedTime.toLocalDate().toDotFormat(),
                bookingStatus.bookedTime.toLocalTime().toDotFormat(),
                bookingStatus.memberCount,
                formattedSeat(bookingStatus.seat.seats),
                theater.name,
                bookingStatus.calculateTicketPrices(),
                )
        view.showConfirmDialog(reservationInfo)
    }

    private fun formattedSeat(seats: List<Seat>): String {
        return seats.joinToString { seat ->
                val rowChar = 'A' + seat.row.value
                val colNumber = seat.column.value + 1
                "$rowChar$colNumber"
            }
    }
}

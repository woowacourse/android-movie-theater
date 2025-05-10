package woowacourse.movie.moviebookingseat

import android.content.Context
import woowacourse.movie.BookingStatusDatabase
import woowacourse.movie.BookingStatusEntity
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.seat.Seat
import kotlin.concurrent.thread

class MovieBookingSeatPresenter(
    private val view: MovieBookingSeat.View,
) : MovieBookingSeat.Presenter {
    private lateinit var bookingStatus: BookingStatus

    override fun loadBookingStatus(bookingStatus: BookingStatus) {
        this.bookingStatus = bookingStatus
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
        view.showConfirmDialog(bookingStatus)
    }

    override fun saveBookingStatus(bookingStatus: BookingStatus, context: Context) {
        thread {
            val database = BookingStatusDatabase.database(context)
            val bookingStatusEntity = BookingStatusEntity.of(bookingStatus)
            database.insert(bookingStatusEntity)
        }
    }
}

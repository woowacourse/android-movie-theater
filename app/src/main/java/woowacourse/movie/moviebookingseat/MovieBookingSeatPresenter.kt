package woowacourse.movie.moviebookingseat

import android.content.Context
import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.MovieApplication
import woowacourse.movie.data.Reservation
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.helper.LocalDateHelper.toDotFormat
import kotlin.concurrent.thread

class MovieBookingSeatPresenter(
    private val view: MovieBookingSeat.View,
) : MovieBookingSeat.Presenter {
    private lateinit var bookingStatus: BookingStatus
    private lateinit var theater: Theater

    override fun loadBookingStatus(
        bookingStatus: BookingStatus,
        theater: Theater,
    ) {
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
        setUpActiveButton()
        view.showTotalPrice(totalPrice)
    }

    private fun setUpActiveButton() {
        if (bookingStatus.seat.isSelectedAll()) view.updateButton()
    }

    override fun confirmBooking(context: Context) {
        val reservation =
            Reservation(
                title = bookingStatus.movie.title,
                date = bookingStatus.bookedTime.toLocalDate().toDotFormat(),
                time = bookingStatus.bookedTime.toLocalTime().toDotFormat(),
                personnel = bookingStatus.memberCount,
                seats = formattedSeat(bookingStatus.seat.seats),
                theater = theater.name,
                price = bookingStatus.calculateTicketPrices(),
            )

        thread {
            val db = (context as MovieApplication).database
            val generatedId = db.reservationDao().insert(reservation)
            reservation.uid = generatedId
            Handler(Looper.getMainLooper()).post {
                view.showConfirmDialog(generatedId)
            }
        }
    }

    private fun formattedSeat(seats: List<Seat>): String {
        return seats.joinToString { seat ->
            val rowChar = 'A' + seat.row.value
            val colNumber = seat.column.value + 1
            "$rowChar$colNumber"
        }
    }
}

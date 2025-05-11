package woowacourse.movie.view.seat

import android.content.Context
import woowacourse.movie.data.datasource.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.view.core.util.DefaultMainThreadExecutor
import woowacourse.movie.view.core.util.MainThreadExecutor
import kotlin.concurrent.thread

class SeatPresenter(
    private val view: SeatContract.View,
    private val seats: Seats,
    private val booking: Booking,
    private val dataSource: TicketDataSource,
    private val mainThreadExecutor: MainThreadExecutor,
) : SeatContract.Presenter {
    init {
        loadBookingInfo()
    }

    private val limit = booking.count.value

    override fun loadBookingInfo() {
        view.showPrice(0)
    }

    override fun changeSeat(position: Seat) {
        val newSeat = Seat(x = Column(position.x.value), y = Row(position.y.value))

        if (!seats.isSelected(newSeat) && !seats.canSelect(limit)) {
            return view.showToast(limit)
        }

        seats.toggleSeat(newSeat)

        view.showSeat(seats.item)
        view.showPrice(seats.bookingPrice())
        updateConfirmButtonState(limit)
    }

    override fun attemptConfirmBooking() {
        if (seats.isNotSelectDone(limit)) {
            return view.showToast(limit)
        }
        saveTicket()
    }

    override fun restore(seat: ArrayList<Seat>) {
        this.seats.restore(seat)
        view.showSeat(seat.toSet())
        view.showPrice(seats.bookingPrice())
        updateConfirmButtonState(limit)
    }

    private fun updateConfirmButtonState(peopleCount: Int) {
        val isEnabled = seats.item.size == peopleCount
        view.setConfirmButtonEnabled(isEnabled)
    }

    private fun saveTicket() {
        thread {
            val id = dataSource.addTicket(booking, seats.item, seats.bookingPrice())
            mainThreadExecutor.execute {
                view.moveToBookingComplete(id)
            }
        }
    }

    companion object {
        fun initialize(
            view: SeatContract.View,
            booking: Booking,
            context: Context,
        ): SeatContract.Presenter {
            val seat = Seats()
            val db = UserDatabase.getDatabase(context)
            val dao = db.ticketDao()
            val dataSource = TicketDataSourceImpl(dao)
            val executor = DefaultMainThreadExecutor()
            return SeatPresenter(view, seat, booking, dataSource, executor)
        }
    }
}

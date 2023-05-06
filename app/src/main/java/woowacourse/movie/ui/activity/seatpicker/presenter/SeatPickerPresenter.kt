package woowacourse.movie.ui.activity.seatpicker.presenter

import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.data.db.SeatContract
import woowacourse.movie.data.db.TicketContract
import woowacourse.movie.data.db.TicketSeatContract
import woowacourse.movie.data.entity.Reservations
import woowacourse.movie.domain.MovieTicket
import woowacourse.movie.ui.activity.seatpicker.SeatPickerContract
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.mapToMovieTicket
import woowacourse.movie.ui.model.mapToMovieTicketModel
import woowacourse.movie.ui.model.mapToMovieTicketModelWithOriginalPrice
import woowacourse.movie.ui.model.seat.SeatModel
import woowacourse.movie.ui.model.seat.mapToSeat

class SeatPickerPresenter(private val view: SeatPickerContract.View) :
    SeatPickerContract.Presenter {
    private lateinit var ticket: MovieTicket

    override fun initTicket(ticketModel: MovieTicketModel) {
        ticket = ticketModel.mapToMovieTicket()
        view.applyTicketData(ticket.mapToMovieTicketModel())
    }

    override fun reserveSeat(seat: SeatModel) {
        if (ticket.canReserveSeat()) {
            addSeat(seat)
            return
        }
        view.notifyUnableToReserveMore()
    }

    private fun addSeat(seat: SeatModel) {
        ticket.reserveSeat(seat.mapToSeat())
        val price = ticket.getDiscountPrice()
        view.setSeatReserved(seat)
        view.updatePrice(price.amount)
    }

    override fun cancelSeat(seat: SeatModel) {
        ticket.cancelSeat(seat.mapToSeat())
        val price = ticket.getDiscountPrice()
        view.setSeatCanceled(seat)
        view.updatePrice(price.amount)
    }

    override fun checkSelectionDone() {
        if (ticket.canReserveSeat()) {
            view.deactivateDoneButton()
        } else {
            view.activateDoneButton()
        }
    }

    override fun addReservation(db: SQLiteDatabase) {
        val ticketModel = ticket.mapToMovieTicketModel()
        Reservations.addItem(ticketModel)
        insertReservationToDatabase(db, ticketModel)
        view.afterReservation(ticketModel)
    }

    override fun getTicketModelWithOriginalPrice(): MovieTicketModel {
        return ticket.mapToMovieTicketModelWithOriginalPrice()
    }

    private fun insertReservationToDatabase(db: SQLiteDatabase, ticket: MovieTicketModel) {
        val ticketId = TicketContract.createRecord(db, ticket)
        ticket.seats.forEach {
            val cursor = SeatContract.readRecordBySeat(db, it)
            cursor.moveToFirst()
            val seatId = cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.COLUMN_ID))
            TicketSeatContract.createRecord(db, ticketId, seatId)
        }
    }
}

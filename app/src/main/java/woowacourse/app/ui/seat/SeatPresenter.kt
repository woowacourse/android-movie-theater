package woowacourse.app.ui.seat

import woowacourse.app.model.BookedMovie
import woowacourse.app.usecase.theater.TheaterUseCase
import woowacourse.domain.BoxOffice
import woowacourse.domain.SelectResult
import woowacourse.domain.SelectedSeat
import woowacourse.domain.movie.Movie
import woowacourse.domain.theater.Theater
import woowacourse.domain.ticket.Seat

class SeatPresenter(
    private val view: SeatContract.View,
    private val boxOffice: BoxOffice,
    private val bookedMovie: BookedMovie,
    theaterUseCase: TheaterUseCase,
) : SeatContract.Presenter {
    private val selectedSeat = SelectedSeat(bookedMovie.ticketCount)
    private val theater: Theater? = theaterUseCase.getTheater(bookedMovie.theaterId)
    override val movie: Movie get() = bookedMovie.movie

    override fun getSelectedSeats(): Set<Seat> {
        return selectedSeat.seats.toSet()
    }

    override fun selectSeat(seat: Seat) {
        val result = selectedSeat.clickSeat(seat)
        when (result) {
            SelectResult.Select.Full -> view.showSeatFull()
            SelectResult.Select.Success -> view.selectSeatView(seat)
            SelectResult.Unselect -> view.selectSeatView(seat)
        }
        view.setConfirmButtonEnable(selectedSeat.isSeatFull)
        setPayment()
    }

    override fun setPayment() {
        val payment = boxOffice.getPayment(
            bookedMovie.movie,
            bookedMovie.bookedDateTime,
            selectedSeat.seats,
        )
        view.setPaymentText(payment)
    }

    override fun setTable() {
        if (theater == null) {
            view.errorControl()
            return
        }
        view.setTableSize(theater.rowSize, theater.columnSize)
        view.setTableColor(
            sRank = theater.sRankRange,
            aRank = theater.aRankRange,
            bRank = theater.bRankRange,
        )
        view.setTableClickListener { theater.selectSeat(it) }
    }

    override fun clickConfirmButton() {
        view.showDialog()
    }

    override fun completeBooking() {
        val reservation = boxOffice.makeReservation(
            bookedMovie.movie,
            bookedMovie.bookedDateTime,
            selectedSeat.seats,
        )
        view.completeBooking(reservation)
    }
}

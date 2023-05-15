package woowacourse.app.presentation.ui.seat

import woowacourse.app.presentation.model.BookedMovie
import woowacourse.domain.BoxOffice
import woowacourse.domain.SelectResult
import woowacourse.domain.SelectedSeat
import woowacourse.domain.movie.Movie
import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository
import woowacourse.domain.ticket.Seat
import woowacourse.domain.util.CgvResult

class SeatPresenter(
    private val view: SeatContract.View,
    private val boxOffice: BoxOffice,
    private val bookedMovie: BookedMovie,
    private val theaterRepository: TheaterRepository,
) : SeatContract.Presenter {
    private val selectedSeat = SelectedSeat(bookedMovie.ticketCount)
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
        val result: CgvResult<Theater> = theaterRepository.getTheater(bookedMovie.theaterId)
        when (result) {
            is CgvResult.Success -> setTable(result.data)
            is CgvResult.Failure -> {
                view.showNoSuchTheater()
                return
            }
        }
    }

    private fun setTable(theater: Theater) {
        view.setTableSize(theater.seatStructure.rowSize, theater.seatStructure.columnSize)
        view.setTableColor(
            sRank = theater.seatStructure.sRankRange,
            aRank = theater.seatStructure.aRankRange,
            bRank = theater.seatStructure.bRankRange,
        )
        view.setTableClickListener { theater.selectSeat(it) }
    }

    override fun clickConfirmButton() {
        view.showBookingConfirmDialog()
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

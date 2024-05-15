package woowacourse.movie.seats.presenter

import android.widget.TextView
import woowacourse.movie.common.CommonDataSource.movieList
import woowacourse.movie.seats.contract.SeatsContract
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.seats.model.SeatsDataSource
import woowacourse.movie.seats.model.SeatsDataSource.movieId
import woowacourse.movie.seats.model.SeatsDataSource.seat
import woowacourse.movie.seats.model.SeatsDataSource.seatTotalPrice
import woowacourse.movie.seats.model.SeatsDataSource.selectedSeats
import woowacourse.movie.seats.model.SeatsDataSource.ticketCount
import woowacourse.movie.seats.model.SeatsDataSource.ticketData

class SeatsPresenter(val view: SeatsContract.View) : SeatsContract.Presenter {
    override fun initCell(cell: TextView) {
        view.initCell(cell, seat)
    }

    override fun createSeat(
        rowIndex: Int,
        colIndex: Int,
    ) {
        seat = Seat.of(rowIndex, colIndex)
    }

    override fun storeTicketCount(ticketCount: Int) {
        SeatsDataSource.ticketCount = ticketCount
    }

    override fun selectSeat(
        rowIndex: Int,
        colIndex: Int,
    ) {
        seat = Seat.of(rowIndex, colIndex)
    }

    override fun startNextActivity() {
        view.startNextActivity(ticketData)
    }

    override fun storeDate(date: String) {
        SeatsDataSource.date = date
    }

    override fun storeTime(time: String) {
        SeatsDataSource.time = time
    }

    override fun setPriceInfo() {
        view.setTotalPrice(seatTotalPrice)
    }

    override fun storeMovieId(movieId: Long) {
        SeatsDataSource.movieId = movieId
    }

    override fun setMovieTitleInfo() {
        view.setMovieTitle(movieList.first { it.id == movieId }.title)
    }

    override fun setSeatsCellsBackgroundColorInfo() {
        seat.select()
        view.setSeatCellBackgroundColor(seat)
        view.setTotalPrice(seatTotalPrice)
    }

    override fun setSeatsTextInfo() {
        view.setSeatsText(seat)
    }

    override fun setConfirmButtonClickListener() {
        if (ticketCount == selectedSeats.size) {
            view.setOnConfirmButtonClickListener()
        } else {
            view.setOffConfirmButtonClickListener()
        }
    }

    override fun clearSelectedSeats() {
        seatTotalPrice = 0
        Seat.seats.clear()
    }

    override fun storeTheaterId(theaterId: Long) {
        SeatsDataSource.theaterId = theaterId
    }
}

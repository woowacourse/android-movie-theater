package woowacourse.movie.seats.contract

import android.widget.TextView
import woowacourse.movie.seats.model.Seat
import java.io.Serializable

interface SeatsContract {
    interface View {
        fun setOnSelectSeat()

        val presenter: Presenter

        fun setSeatCellBackgroundColor(info: Seat)

        fun setMovieTitle(info: String)

        fun setTotalPrice(info: Int)

        fun setSeatsText(info: Seat)

        fun startNextActivity(ticketData: Serializable)

        fun initCell(
            cell: TextView,
            seat: Seat,
        )

        fun setOnConfirmButtonClickListener()
        fun setOffConfirmButtonClickListener()
    }

    interface Presenter {
        fun setPriceInfo()

        fun setSeatsCellsBackgroundColorInfo()

        fun createSeat(
            rowIndex: Int,
            colIndex: Int,
        )

        fun setSeatsTextInfo()

        fun setMovieTitleInfo()

        fun storeMovieId(movieId: Long)

        fun startNextActivity()

        fun storeDate(date: String)

        fun storeTime(time: String)

        fun selectSeat(
            rowIndex: Int,
            colIndex: Int,
        )

        fun initCell(cell: TextView)
        fun storeTicketCount(ticketCount: Int)
        fun setConfirmButtonClickListener()
        fun clearSelectedSeats()
        fun storeTheaterId(theaterId: Long)
    }
}

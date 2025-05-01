package woowacourse.movie.presentation.view.home.reservation.seat

import android.content.Context
import android.widget.TableRow
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.SeatUiModel

class ReservationSeatViews(
    private val context: Context,
    private val binding: FragmentReservationSeatBinding,
) {
    private val cachedSeatViews = mutableMapOf<SeatUiModel, TextView>()
    private val seatViewFactory = SeatViewFactory(context)

    fun setData(
        screen: ScreenUiModel,
        selectedSeats: List<SeatUiModel>,
    ) {
        binding.tbSeats.removeAllViews()
        renderSeatLayout(screen.seats, selectedSeats)
    }

    fun setSeatListeners(onClickSeat: (SeatUiModel) -> Unit) {
        cachedSeatViews.forEach { (seat, view) ->
            view.setOnClickListener { onClickSeat(seat) }
        }
    }

    fun updateSeatState(seat: SeatUiModel) {
        cachedSeatViews[seat]?.toggleSelection()
    }

    fun findSelectedViews(): List<SeatUiModel> = cachedSeatViews.filterValues { it.isSelected() }.keys.toList()

    private fun renderSeatLayout(
        seats: List<SeatUiModel>,
        selectedSeats: List<SeatUiModel>,
    ) {
        var currentRow = -1
        var tableRow: TableRow? = null

        for (seat in seats) {
            if (seat.row != currentRow) {
                tableRow?.let { binding.tbSeats.addView(it) }
                tableRow = TableRow(context)
                currentRow = seat.row
            }

            val seatView = seatViewFactory.create(seat, selectedSeats.contains(seat))
            cachedSeatViews[seat] = seatView
            tableRow?.addView(seatView)
        }

        tableRow?.let { binding.tbSeats.addView(it) }
    }

    private fun TextView.isSeatSelected(): Boolean = getTag(R.id.seat_selected) as? Boolean ?: false

    private fun TextView.toggleSelection() {
        val current = isSeatSelected()
        setTag(R.id.seat_selected, !current)
        setBackgroundResource(if (current) R.color.white else R.color.yellow_fa)
    }
}

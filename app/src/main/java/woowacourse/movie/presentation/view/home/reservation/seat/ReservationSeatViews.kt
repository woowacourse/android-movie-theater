package woowacourse.movie.presentation.view.home.reservation.seat

import android.content.Context
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.SeatTypeUiModel
import woowacourse.movie.presentation.model.SeatUiModel
import woowacourse.movie.presentation.util.CustomAlertDialog

class ReservationSeatViews(
    private val context: Context,
    private val binding: FragmentReservationSeatBinding,
) {
    private val cachedSeatViews: MutableMap<SeatUiModel, TextView> = mutableMapOf()

    val dialog: CustomAlertDialog by lazy { CustomAlertDialog(context) }

    fun setData(
        screen: ScreenUiModel,
        selectedSeats: List<SeatUiModel>,
    ) {
        renderSeatLayout(screen, selectedSeats)
    }

    fun setEventListeners(
        onClickConfirm: () -> Unit,
        onClickSeat: (SeatUiModel) -> Unit,
    ) {
        cachedSeatViews.forEach { (seat, view) -> view.setupSeatClickListener(seat, onClickSeat) }
        binding.btnConfirm.setOnClickListener { onClickConfirm() }
    }

    fun updateSeatState(seat: SeatUiModel) {
        cachedSeatViews[seat]?.toggleSeatBackgroundColor()
    }

    fun updateConfirmButton(canPublish: Boolean) {
        val color = if (canPublish) R.color.purple_62 else R.color.gray_b7
        binding.btnConfirm.apply {
            isEnabled = canPublish
            setBackgroundResource(color)
        }
    }

    fun findSelectedViews(): List<SeatUiModel> =
        cachedSeatViews
            .filter {
                it.value.isSeatSelected()
            }.map { it.key }

    private fun renderSeatLayout(
        screen: ScreenUiModel,
        selectedSeats: List<SeatUiModel>,
    ) {
        var currentRow = -1
        var tableRow: TableRow? = null

        screen.seats.forEach { seat ->
            if (seat.row != currentRow) {
                tableRow?.let { addRowToLayout(it) }
                tableRow = createTableRow()
                currentRow = seat.row
            }
            val seatView = createSeatView(seat, selectedSeats)
            tableRow?.addView(seatView)
        }
        tableRow?.let { addRowToLayout(it) }
    }

    private fun addRowToLayout(row: TableRow) {
        binding.tbSeats.addView(row)
    }

    private fun createTableRow(): TableRow = TableRow(context)

    private fun createSeatView(
        seat: SeatUiModel,
        selectedSeats: List<SeatUiModel>,
    ): TextView =
        TextView(context).apply {
            setupSeatText(seat)
            setupSeatStyle(seat, selectedSeats.contains(seat))
            setupSeatLayoutParams()
            cachedSeatViews[seat] = this
        }

    private fun TextView.setupSeatText(seat: SeatUiModel) {
        text = seat.toLabel()
        gravity = Gravity.CENTER
        textSize = 22f
    }

    private fun TextView.setupSeatStyle(
        seat: SeatUiModel,
        isSelected: Boolean,
    ) {
        setTextColor(seat.type.getSeatColor())
        if (isSelected) {
            toggleSeatBackgroundColor()
            return
        }

        this.setBackgroundResource(R.color.white)
    }

    private fun TextView.setupSeatLayoutParams() {
        layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        post {
            val width = width
            layoutParams.height = width
            requestLayout()
        }
    }

    private fun TextView.setupSeatClickListener(
        seat: SeatUiModel,
        onClickSeat: (SeatUiModel) -> Unit,
    ) {
        tag = seat
        setOnClickListener { onClickSeat(seat) }
    }

    private fun SeatTypeUiModel.getSeatColor(): Int =
        when (this) {
            SeatTypeUiModel.S_CLASS -> context.getColor(R.color.purple_8e)
            SeatTypeUiModel.A_CLASS -> context.getColor(R.color.green_19)
            SeatTypeUiModel.B_CLASS -> context.getColor(R.color.blue_1b)
        }

    private fun TextView.toggleSeatBackgroundColor() {
        val isSelected = this.isSeatSelected()
        val newColorRes = if (isSelected) R.color.white else R.color.yellow_fa
        setTag(id, !isSelected)
        setBackgroundResource(newColorRes)
    }

    private fun TextView.isSeatSelected(): Boolean = this.getTag(id) as? Boolean ?: false
}

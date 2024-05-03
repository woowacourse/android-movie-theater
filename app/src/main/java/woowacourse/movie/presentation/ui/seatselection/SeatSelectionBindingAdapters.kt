package woowacourse.movie.presentation.ui.seatselection

import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.domain.model.SeatRank
import woowacourse.movie.presentation.utils.currency
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("startDate", "endDate")
fun TextView.formatScreeningDate(
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedStartDate = startDate.format(formatter)
    val formattedEndDate = endDate.format(formatter)
    val resultText =
        (this.context).getString(R.string.screening_period, formattedStartDate, formattedEndDate)
    this.text = resultText
}

@BindingAdapter("bindFormatPrice")
fun TextView.formatPrice(price: Int) {
    this.text = price.currency(this.context)
}

@BindingAdapter("bindChangeEnabled")
fun Button.changeEnabled(seatSelectionModel: SeatSelectionUiModel) {
    this.isEnabled = seatSelectionModel.userSeat.seatModels.filter { it.isSelected }.size == seatSelectionModel.ticketCount
}

@BindingAdapter("bindShowSeat")
fun TextView.showSeat(seatModel: SeatModel) {
    this.text = "${seatModel.column}${seatModel.row + 1}"
    this.setTextColor(seatModel.seatRank.toColor(this.context))
}

fun SeatRank.toColor(context: Context): Int {
    return when (this) {
        SeatRank.B -> context.getColor(R.color.purple)
        SeatRank.S -> context.getColor(R.color.green)
        SeatRank.A -> context.getColor(R.color.blue)
    }
}

@BindingAdapter("bindChangeColor")
fun TextView.changeColor(seatModel: SeatModel) {
    this.isSelected = seatModel.isSelected
}

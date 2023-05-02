package woowacourse.movie.feature.reservationList.viewHolder

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.util.DateTimeFormatters
import java.time.LocalDateTime

class TicketsViewHolder(private val binding: ItemReservationLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TicketsItemModel) {
        binding.tickets = item
    }
}

@BindingAdapter("screeningDateTime")
fun setScreeningDateTime(view: TextView, dateTime: LocalDateTime) {
    view.text = view.context.getString(R.string.date_time_form)
        .format(
            DateTimeFormatters.convertToDate(dateTime.toLocalDate()),
            dateTime.toLocalTime().toString()
        )
}

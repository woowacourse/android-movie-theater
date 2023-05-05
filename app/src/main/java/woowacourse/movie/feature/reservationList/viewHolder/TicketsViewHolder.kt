package woowacourse.movie.feature.reservationList.viewHolder

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.util.DateTimeFormatters
import java.time.LocalDateTime

class TicketsViewHolder(binding: ItemReservationLayoutBinding) :
    CommonItemViewHolder(binding) {

    override fun bind(itemModel: CommonItemModel) {
        val item = itemModel as TicketsItemModel
        val binding = binding as ItemReservationLayoutBinding
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

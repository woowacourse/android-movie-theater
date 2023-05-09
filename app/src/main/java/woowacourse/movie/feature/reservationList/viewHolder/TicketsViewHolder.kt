package woowacourse.movie.feature.reservationList.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.util.DateTimeFormatters

class TicketsViewHolder(binding: ViewDataBinding) :
    CommonItemViewHolder(binding) {

    override fun bind(itemModel: CommonItemModel) {
        itemModel as TicketsItemModel
        binding as ItemReservationLayoutBinding
        binding.tickets = itemModel
        binding.screeningDateTimeTextView.text =
            binding.root.context.getString(R.string.date_time_form)
                .format(
                    DateTimeFormatters.convertToDate(itemModel.ticketsState.dateTime.toLocalDate()),
                    itemModel.ticketsState.dateTime.toLocalTime().toString()
                )
    }
}

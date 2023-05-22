package woowacourse.movie.feature.common.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.itemModel.TicketsItemModel

class TicketsViewHolder(binding: ViewDataBinding) : ItemViewHolder(binding) {

    override fun bind(itemModel: ItemModel) {
        binding as ItemReservationBinding
        itemModel as TicketsItemModel
        val context = binding.screeningDateTimeTextView.context

        binding.screeningDateTimeTextView.text = context.getString(R.string.date_time_form)
            .format(
                DateTimeFormatters.convertToDate(itemModel.ticketsState.dateTime.toLocalDate()),
                itemModel.ticketsState.dateTime.toLocalTime().toString()
            )
        binding.movieNameTextView.text = itemModel.ticketsState.movie.title

        binding.reservationItemBox.setOnClickListener { itemModel.onClick(itemModel.ticketsState) }
    }
}

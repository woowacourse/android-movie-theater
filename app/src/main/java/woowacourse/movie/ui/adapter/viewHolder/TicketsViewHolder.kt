package woowacourse.movie.ui.adapter.viewHolder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.ui.DateTimeFormatters
import woowacourse.movie.ui.adapter.itemModel.ItemModel
import woowacourse.movie.ui.adapter.itemModel.TicketsItemModel

class TicketsViewHolder(
    view: View,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(view) {

    private val box: LinearLayout
    private val screeningDateTimeTextView: TextView
    private val movieNameTextView: TextView

    init {
        box = view.findViewById(R.id.reservation_item_box)
        screeningDateTimeTextView = view.findViewById(R.id.screening_date_time_text_view)
        movieNameTextView = view.findViewById(R.id.movie_name_text_view)
        box.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as TicketsItemModel
        val context = screeningDateTimeTextView.context

        screeningDateTimeTextView.text = context.getString(R.string.date_time_form)
            .format(
                DateTimeFormatters.convertToDate(item.ticketsState.dateTime.toLocalDate()),
                item.ticketsState.dateTime.toLocalTime().toString()
            )
        movieNameTextView.text = item.ticketsState.movieName
    }
}

package woowacourse.movie.feature.reservationList.viewHolder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.util.DateTimeFormatters

class TicketsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val box: LinearLayout = view.findViewById(R.id.reservation_item_box)
    private val screeningDateTimeTextView: TextView =
        view.findViewById(R.id.screening_date_time_text_view)
    private val movieNameTextView: TextView = view.findViewById(R.id.movie_name_text_view)

    fun bind(item: TicketsItemModel) {
        val context = screeningDateTimeTextView.context

        screeningDateTimeTextView.text = context.getString(R.string.date_time_form)
            .format(
                DateTimeFormatters.convertToDate(item.ticketsState.dateTime.toLocalDate()),
                item.ticketsState.dateTime.toLocalTime().toString()
            )
        movieNameTextView.text = item.ticketsState.movieState.title

        box.setOnClickListener { item.onClick(bindingAdapterPosition) }
    }
}

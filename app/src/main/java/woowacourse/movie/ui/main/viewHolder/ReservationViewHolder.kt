package woowacourse.movie.ui.main.viewHolder

import android.view.View
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.ui.DateTimeFormatters
import woowacourse.movie.ui.main.itemModel.ItemModel
import woowacourse.movie.ui.main.itemModel.ReservationItemModel

class ReservationViewHolder(view: View) : ItemViewHolder(view) {

    private val screeningDateTimeTextView: TextView
    private val movieNameTextView: TextView

    init {
        screeningDateTimeTextView = view.findViewById(R.id.screening_date_time_text_view)
        movieNameTextView = view.findViewById(R.id.movie_name_text_view)
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as ReservationItemModel
        val context = screeningDateTimeTextView.context

        screeningDateTimeTextView.text = context.getString(R.string.date_time_form)
            .format(
                DateTimeFormatters.convertToDate(item.reservationState.dateTime.toLocalDate()),
                item.reservationState.dateTime.toLocalTime().toString()
            )
        movieNameTextView.text = item.reservationState.movieState.title
    }
}

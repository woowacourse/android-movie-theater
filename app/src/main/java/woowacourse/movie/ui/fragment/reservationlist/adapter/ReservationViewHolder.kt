package woowacourse.movie.ui.fragment.reservationlist.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.TicketTimeModel
import java.time.format.DateTimeFormatter

class ReservationViewHolder(
    view: View,
    onItemViewClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val dateTimeView: TextView by lazy { view.findViewById(R.id.reservation_date_time) }
    private val titleView: TextView by lazy { view.findViewById(R.id.reservation_title) }

    init {
        view.setOnClickListener {
            onItemViewClick(absoluteAdapterPosition)
        }
    }

    fun bind(item: MovieTicketModel) {
        dateTimeView.text = item.time.format()
        titleView.text = item.title
    }

    private fun TicketTimeModel.format(): String =
        dateTime.format(DateTimeFormatter.ofPattern(dateTimeView.context.getString(R.string.date_time_format)))
}

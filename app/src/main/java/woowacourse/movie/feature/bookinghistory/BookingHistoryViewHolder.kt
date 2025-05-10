package woowacourse.movie.feature.bookinghistory

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryViewHolder(
    private val view: View,
) : RecyclerView.ViewHolder(view) {
    private val bookingDetails: TextView = view.findViewById(R.id.tv_date_time_theater_name)
    private val title: TextView = view.findViewById(R.id.tv_movie_title)

    fun bind(bookingHistoryDetails: BookingInfoUiModel) {
        bookingDetails.text =
            view.context.getString(
                R.string.date_time_theater,
                bookingHistoryDetails.date,
                bookingHistoryDetails.movieTime,
                bookingHistoryDetails.theaterName,
            )

        title.text = bookingHistoryDetails.movie.title
    }
}

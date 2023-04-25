package woowacourse.movie.movie.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.MovieDateDto
import woowacourse.movie.dto.MovieTimeDto
import woowacourse.movie.movie.dto.BookingMovieDto
import java.time.format.DateTimeFormatter

class HistoryViewHolder(
    private val view: View,
) :
    RecyclerView.ViewHolder(view) {
    val reservationTime: TextView = itemView.findViewById(R.id.history_datetime)
    val reservationMovie: TextView = itemView.findViewById(R.id.history_title)

    fun bind(item: BookingMovieDto) {
        reservationTime.text = formatMovieDateTime(item.date, item.time)
        reservationMovie.text = item.movie.title
    }

    private fun formatMovieDateTime(date: MovieDateDto, time: MovieTimeDto): String {
        val formatDate =
            date.date.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.date_format)))
        val formatTime =
            time.time.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.time_format)))

        return view.context.getString(R.string.reservation_datetime, formatDate, formatTime)
    }
}

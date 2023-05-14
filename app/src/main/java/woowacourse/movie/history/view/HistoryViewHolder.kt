package woowacourse.movie.history.view

import androidx.recyclerview.widget.RecyclerView
import domain.BookingMovie
import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import woowacourse.movie.R
import woowacourse.movie.databinding.HistoryItemBinding
import java.time.format.DateTimeFormatter

class HistoryViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BookingMovie) {
        binding.historyDatetime.text = formatMovieDateTime(item.date, item.time)

        binding.historyTitle.text = item.title
    }

    private fun formatMovieDateTime(date: MovieDate, time: MovieTime): String {
        val formatDate =
            date.date.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        val formatTime =
            time.time.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.time_format)))

        return binding.root.context.getString(R.string.reservation_datetime, formatDate, formatTime)
    }
}

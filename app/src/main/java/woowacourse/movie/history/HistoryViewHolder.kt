package woowacourse.movie.history

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.dto.movie.MovieDateDto
import woowacourse.movie.dto.movie.MovieTimeDto
import java.time.format.DateTimeFormatter

class HistoryViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BookingMovieEntity) {
        binding.historyDatetime.text = formatMovieDateTime(item.date, item.time)
        binding.historyTitle.text = item.title
    }

    private fun formatMovieDateTime(date: MovieDateDto, time: MovieTimeDto): String {
        val formatDate =
            date.date.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        val formatTime =
            time.time.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.time_format)))

        return binding.root.context.getString(R.string.reservation_datetime, formatDate, formatTime)
    }
}

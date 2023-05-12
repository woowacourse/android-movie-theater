package woowacourse.movie.fragment.history.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.util.listener.OnClickListener
import java.time.format.DateTimeFormatter

class HistoryViewHolder private constructor(
    private val binding: HistoryItemBinding,
    onItemViewClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.view = this
        binding.root.setOnClickListener {
            onItemViewClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(item: BookingMovieUIModel) {
        binding.history = item
    }

    fun formatMovieDateTime(date: MovieDateUIModel, time: MovieTimeUIModel): String {
        val formatDate =
            date.date.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        val formatTime =
            time.time.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.time_format)))

        return binding.root.context.getString(R.string.reservation_datetime, formatDate, formatTime)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onItemViewClickListener: OnClickListener<Int>
        ): HistoryViewHolder {
            val binding = HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HistoryViewHolder(binding, onItemViewClickListener)
        }
    }
}

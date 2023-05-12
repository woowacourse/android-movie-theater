package woowacourse.movie.fragment.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.util.listener.OnClickListener
import java.time.format.DateTimeFormatter

class MovieViewHolder private constructor(
    private val binding: MovieItemBinding,
    onItemClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.view = this
        binding.bookButton.setOnClickListener {
            onItemClickListener.onClick(adapterPosition)
        }
    }

    fun bind(movie: MovieUIModel) {
        binding.moviePoster.setImageResource(movie.moviePoster)
        binding.movie = movie
    }

    fun formatMovieRunningDate(item: MovieUIModel): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        return binding.root.context.getString(R.string.movie_running_date, startDate, endDate)
    }

    fun formatMovieRunningTime(item: MovieUIModel): String {
        return binding.root.context.getString(R.string.movie_running_time).format(item.runningTime)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onItemClickListener: OnClickListener<Int>,
        ): MovieViewHolder {
            val binding =
                MovieItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return MovieViewHolder(binding, onItemClickListener)
        }
    }
}

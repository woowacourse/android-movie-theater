import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.movielist.OnClickListener
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val onMovieClickListener: OnClickListener<MovieDto>,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieDto) {
        binding.moviePoster.setImageResource(movie.moviePoster)
        binding.movieTitle.text = movie.title
        binding.movieDate.text = formatMovieRunningDate(movie)
        binding.runningTime.text = formatMovieRunningTime(movie)

        binding.bookButton.setOnClickListener {
            onMovieClickListener.onClick(movie)
        }
    }

    private fun formatMovieRunningDate(item: MovieDto): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        return binding.root.context.getString(R.string.movie_running_date, startDate, endDate)
    }

    private fun formatMovieRunningTime(item: MovieDto): String {
        return binding.root.context.getString(R.string.movie_running_time).format(item.runningTime)
    }
}

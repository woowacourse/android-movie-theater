package woowacourse.movie.view.movies.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.movietime.Date
import woowacourse.movie.view.movies.OnMovieEventListener
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    eventListener: OnMovieEventListener,
    binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var movie: Movie? = null
    private val movieImage: ImageView = binding.ivMovieImage
    private val movieTitle: TextView = binding.tvMovieTitle
    private val movieDate: TextView = binding.tvMovieDate
    private val movieTime: TextView = binding.tvMovieTime

    init {
        binding.btnReserve.setOnClickListener {
            movie?.let { eventListener.onClickShowTheater(it) }
        }
    }

    fun bind(movie: Movie) {
        this.movie = movie
        movieImage.setImageResource(movie.image)
        movieTitle.text = movie.title
        setDateTextView(movie.date)
        setTimeTextView(movie.time)
    }

    private fun setDateTextView(date: Date) {
        val formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN)
        val startDateFormatted = date.startDate.format(formatter)
        val endDateFormatted = date.endDate.format(formatter)
        movieDate.text =
            itemView.context?.getString(R.string.movieDate, startDateFormatted, endDateFormatted)
    }

    private fun setTimeTextView(time: Int) {
        movieTime.text =
            itemView.context?.getString(R.string.movieTime, time.toString())
    }

    companion object {
        private const val DATETIME_PATTERN = "yyyy.M.d"
    }
}

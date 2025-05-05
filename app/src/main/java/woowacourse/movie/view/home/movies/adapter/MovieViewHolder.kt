package woowacourse.movie.view.home.movies.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.movietime.Date
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.OnMovieEventListener
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    eventListener: OnMovieEventListener,
    binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var movieUi: MovieUi? = null
    private val movieImage: ImageView = binding.ivMovieImage
    private val movieTitle: TextView = binding.tvMovieTitle
    private val movieDate: TextView = binding.tvMovieDate
    private val movieTime: TextView = binding.tvMovieTime

    init {
        binding.btnReserve.setOnClickListener {
            movieUi?.let { eventListener.onClickShowTheater(it) }
        }
    }

    fun bind(movieUi: MovieUi) {
        this.movieUi = movieUi
        movieImage.setImageResource(movieUi.image)
        movieTitle.text = movieUi.title
        setDateTextView(movieUi.date)
        setTimeTextView(movieUi.time)
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

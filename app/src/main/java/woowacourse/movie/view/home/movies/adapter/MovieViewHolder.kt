package woowacourse.movie.view.home.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.OnMovieEventListener

class MovieViewHolder(
    eventListener: OnMovieEventListener,
    val binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var movieUi: MovieUi? = null

    init {
        binding.btnReserve.setOnClickListener {
            movieUi?.let { eventListener.onClickShowTheater(it) }
        }
    }

    fun bind(movieUi: MovieUi) {
        this.movieUi = movieUi
        binding.ivMovieImage.setImageResource(movieUi.image)
        binding.tvMovieTitle.text = movieUi.title
        binding.tvMovieDate.text = movieUi.formattedDate(DATETIME_PATTERN)
        setTimeTextView(movieUi.time)
    }

    private fun setTimeTextView(time: Int) {
        binding.tvMovieTime.text =
            itemView.context?.getString(R.string.movieTime, time.toString())
    }

    companion object {
        private const val DATETIME_PATTERN = "yyyy.M.d"
    }
}

package woowacourse.movie.view.home.movies.adapter

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.OnMovieEventListener

class MovieViewHolder(
    eventListener: OnMovieEventListener,
    val binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var movieUi: MovieUi? = null

    init {
        binding.onConfirm = OnClickListener { movieUi?.let { eventListener.onClickShowTheater(it) } }
    }

    fun bind(movieUi: MovieUi) {
        this.movieUi = movieUi
        binding.movieUi = movieUi
    }
}

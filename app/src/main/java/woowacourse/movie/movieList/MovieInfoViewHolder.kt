package woowacourse.movie.movieList

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.view.widget.MovieView
import woowacourse.movie.databinding.ItemMovieBinding

class MovieInfoViewHolder(binding: ItemMovieBinding, onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    private val movieView: MovieView = MovieView(
        poster = binding.itemMoviePoster,
        title = binding.itemMovieTitle,
        date = binding.itemMovieDate,
        runningTime = binding.itemMovieRunningTime
    )

    init {
        binding.itemMovieReservationButton.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(movieViewData: MovieViewData) {
        movieView.bind(movieViewData)
    }
}

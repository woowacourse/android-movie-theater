package woowacourse.movie.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.view.widget.MovieController
import woowacourse.movie.view.widget.MovieView

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
        MovieController.bind(movieViewData, movieView)
    }
}

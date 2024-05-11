package woowacourse.movie.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieCatalogBinding
import woowacourse.movie.model.movie.Movie

typealias OnMovieSelected = (movieId: Int) -> Unit

class MovieViewHolder(
    private val binding: ItemMovieCatalogBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        onMovieSelected: OnMovieSelected,
    ) {
        with(binding) {
            this.movie = movie
            screeningStartDate = movie.screeningPeriod.first()
            screeningEndDate = movie.screeningPeriod.last()
            buttonMovieCatalogReservation.setOnClickListener { onMovieSelected(movie.id) }
        }
    }
}

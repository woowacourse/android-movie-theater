package woowacourse.movie.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieCatalogBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.utils.MovieUtils.convertPeriodFormat

typealias OnMovieSelected = (movieId: Int) -> Unit

class MovieViewHolder(
    private val binding: ItemMovieCatalogBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.movieCatalog = this
    }

    fun bind(
        item: Movie,
        onMovieSelected: OnMovieSelected,
    ) {
        with(binding) {
            itemMovieCatalogTextViewTitle.text = item.title
            itemMovieCatalogImageViewPoster.setImageResource(item.posterId)
            itemMovieCatalogTextViewScreeningDate.text = convertPeriodFormat(item.screeningPeriod)
            itemMovieCatalogTextViewRunningTime.text = item.runningTime
            itemMovieCatalogButtonReservation.setOnClickListener { onMovieSelected(item.id) }
        }
    }
}

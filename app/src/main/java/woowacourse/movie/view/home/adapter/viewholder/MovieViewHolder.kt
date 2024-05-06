package woowacourse.movie.view.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieCatalogBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.utils.MovieUtils
import woowacourse.movie.view.home.adapter.OnClickReservationButton

class MovieViewHolder(
    private val binding: ItemMovieCatalogBinding,
    private val onClickReservationButton: OnClickReservationButton,
) : RecyclerView.ViewHolder(binding.root) {
    var screeningFormatText = ""

    init {
        binding.movieCatalog = this
    }

    fun bind(item: Movie) {
        binding.movie = item
        screeningFormatText =
            MovieUtils.convertPeriodFormat(item.screeningPeriod, binding.root.context)
    }

    fun onClickMovie(item: Movie) = onClickReservationButton(item)
}

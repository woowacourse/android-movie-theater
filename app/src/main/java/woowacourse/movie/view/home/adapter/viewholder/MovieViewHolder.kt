package woowacourse.movie.view.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieCatalogBinding
import woowacourse.movie.model.movie.Movie

class MovieViewHolder(
    private val binding: ItemMovieCatalogBinding,
) : RecyclerView.ViewHolder(binding.root) {
    var screeningFormatText = ""

    init {
        binding.movieCatalog = this
    }

    fun bind(
        item: Movie,
        onClickMovie: (Movie) -> Unit,
    ) {
        binding.movie = item
        binding.itemMovieCatalogButtonReservation.setOnClickListener { onClickMovie(item) }
    }
}

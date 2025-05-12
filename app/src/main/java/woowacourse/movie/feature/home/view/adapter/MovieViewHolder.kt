package woowacourse.movie.feature.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.feature.model.MovieUiModel

class MovieViewHolder(
    parent: ViewGroup,
    handler: Handler,
) : ContentViewHolder<ContentItem.Movie, ItemMovieBinding>(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.handler = handler
    }

    override fun bind(item: ContentItem.Movie) {
        super.bind(item)
        binding.movie = item.value
        binding.executePendingBindings()
    }

    interface Handler {
        fun onBookingClick(movie: MovieUiModel)
    }
}

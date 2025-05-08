package woowacourse.movie.presentation.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.MovieUiModel

class MovieViewHolder(
    parent: ViewGroup,
    private val handler: Handler,
) : BaseViewHolder<MovieUiModel, ItemMovieBinding>(
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    init {
        binding.btnMovieReservation.setOnClickListener {
            handler.onMovieClicked(item)
        }
    }

    override fun bind(item: MovieUiModel) {
        super.bind(item)
        binding.movie = item
        binding.executePendingBindings()
    }

    interface Handler {
        fun onMovieClicked(movie: MovieUiModel)
    }
}

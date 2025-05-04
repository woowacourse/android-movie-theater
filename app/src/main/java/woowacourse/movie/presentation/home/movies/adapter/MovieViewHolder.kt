package woowacourse.movie.presentation.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.home.movies.adapter.item.MovieItem

class MovieViewHolder(
    view: ViewGroup,
    private val eventListener: OnMovieEventListener,
) : BaseViewHolder<MovieItem, ItemMovieBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.item_movie, view, false),
    ) {
    override fun bind(item: MovieItem) {
        binding.itemMovie = item.movie
        binding.onClickListener = eventListener
    }

    interface OnMovieEventListener {
        fun onClick(movie: MovieUiModel)
    }
}

package woowacourse.movie.presentation.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem

class MovieViewHolder(
    view: ViewGroup,
    eventListener: OnMovieEventListener,
) : BaseViewHolder<MovieMainItem.MovieItem, ItemMovieBinding>(
    DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.item_movie, view, false),
) {
    init {
        binding.onClickListener = eventListener
    }

    override fun bind(item: MovieMainItem.MovieItem) {
        binding.itemMovie = item.movie
    }

    interface OnMovieEventListener {
        fun onMovieClick(movie: MovieUiModel)
    }
}

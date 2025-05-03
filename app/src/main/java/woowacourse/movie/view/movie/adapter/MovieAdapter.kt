package woowacourse.movie.view.movie.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import woowacourse.movie.view.base.BaseListAdapter
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.model.MovieListItem

class MovieAdapter(
    private val handler: Handler,
) : BaseListAdapter<MovieListItem, BaseViewHolder<MovieListItem, ViewBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<MovieListItem, ViewBinding> =
        when (MovieListItem.ItemViewType.entries[viewType]) {
            MovieListItem.ItemViewType.MOVIE_ITEM -> MovieViewHolder(parent, handler)
            MovieListItem.ItemViewType.AD_ITEM -> AdViewHolder(parent)
        } as BaseViewHolder<MovieListItem, ViewBinding>

    interface Handler : MovieViewHolder.Handler
}

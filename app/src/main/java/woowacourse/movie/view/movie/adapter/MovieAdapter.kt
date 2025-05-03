package woowacourse.movie.view.movie.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import woowacourse.movie.view.base.BaseListAdapter
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.model.MainItem

class MovieAdapter(
    private val handler: Handler,
) : BaseListAdapter<MainItem, BaseViewHolder<MainItem, ViewBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<MainItem, ViewBinding> =
        when (MainItem.ItemViewType.entries[viewType]) {
            MainItem.ItemViewType.MOVIE_ITEM -> MovieViewHolder(parent, handler)
            MainItem.ItemViewType.AD_ITEM -> AdViewHolder(parent)
        } as BaseViewHolder<MainItem, ViewBinding>

    interface Handler : MovieViewHolder.Handler
}

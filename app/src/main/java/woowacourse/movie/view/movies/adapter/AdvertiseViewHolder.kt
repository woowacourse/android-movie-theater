package woowacourse.movie.view.movies.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.movies.adapter.model.MovieRvItem

class AdvertiseViewHolder(
    parent: ViewGroup,
    @LayoutRes viewType: Int,
) : BaseViewHolder<MovieRvItem.AdItem>(parent, viewType) {
    override fun bind(item: MovieRvItem.AdItem) {
        AdvertisementItemBinding.bind(itemView).apply {
            model = item
        }
    }
}

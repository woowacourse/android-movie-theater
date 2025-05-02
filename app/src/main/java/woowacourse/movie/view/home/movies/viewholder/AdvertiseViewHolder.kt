package woowacourse.movie.view.home.movies.viewholder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.home.movies.model.MovieRvItem

class AdvertiseViewHolder(
    parent: ViewGroup,
    @LayoutRes viewType: Int,
) : BaseViewHolder<MovieRvItem.AdItem>(parent, viewType) {
    override fun bind(movieRvItem: MovieRvItem.AdItem) {
        AdvertisementItemBinding.bind(itemView).apply {
            model = movieRvItem
        }
    }
}

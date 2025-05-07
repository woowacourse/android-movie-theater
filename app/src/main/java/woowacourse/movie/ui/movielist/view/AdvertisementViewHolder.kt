package woowacourse.movie.ui.movielist.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.domain.model.Advertisement

class AdvertisementViewHolder(
    private val itemBinding: AdvertisementItemBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: Advertisement) {
        itemBinding.advertisement = item
    }
}

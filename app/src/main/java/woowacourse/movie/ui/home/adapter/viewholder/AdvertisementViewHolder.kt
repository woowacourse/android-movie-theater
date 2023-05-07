package woowacourse.movie.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.model.MovieListModel.AdModel

class AdvertisementViewHolder(
    private val binding: ItemAdBinding,
    private val onClick: (AdModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(advertisement: AdModel) {
        with(binding) {
            root.setOnClickListener { onClick(advertisement) }
            itemBanner.setImageResource(advertisement.banner)
        }
    }
}

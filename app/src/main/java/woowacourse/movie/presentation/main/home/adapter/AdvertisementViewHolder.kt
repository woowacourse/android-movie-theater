package woowacourse.movie.presentation.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.domain.model.home.HomeItem

class AdvertisementViewHolder(private val binding: AdvertisementItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(advertisement: HomeItem.AdvertisementItem) {
        binding.advertisementImage.setImageResource(advertisement.resourceId)
    }
}

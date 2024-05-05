package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderAdvertisementBinding
import woowacourse.movie.domain.model.ScreenAd

class AdvertisementViewHolder(
    private val onItemClick: (id: Int) -> Unit,
    private val binding: HolderAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(advertisement: ScreenAd.Advertisement) {
        binding.advertisement = advertisement

        binding.ivAdvertisement.setOnClickListener {
            onItemClick(advertisement.id)
        }
    }
}

package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderAdvertisementBinding
import woowacourse.movie.domain.model.ScreenAd

class AdvertisementViewHolder(
    private val onItemClick: (id: Int) -> Unit,
    private val binding: HolderAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ad: ScreenAd.Advertisement) {
        binding.advertisementAd = ad

        binding.ivAdvertisement.setOnClickListener {
            onItemClick(ad.id)
        }
    }
}

package woowacourse.movie.view.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.model.advertisement.Advertisement

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.advertisement
    }

    fun bind(ads: Advertisement) {
        binding.imageViewItemAdvertisement.setImageResource(ads.banner)
    }
}

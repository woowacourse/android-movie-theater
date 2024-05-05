package woowacourse.movie.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.model.advertisement.Advertisement

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewHolder = this
    }

    fun bind(advertisement: Advertisement) {
        binding.advertisement = advertisement
    }
}

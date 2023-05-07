package woowacourse.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.AdvertisementItemModel
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementItemViewHolder(private val binding: ItemAdvertisementBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AdvertisementItemModel) {
        binding.advertisement = item
    }
}

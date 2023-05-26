package woowacourse.movie.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.model.itemmodel.AdvertisementItemModel
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_advertisement, parent, false)
) {
    private val binding = ItemAdvertisementBinding.bind(itemView)

    fun bind(item: AdvertisementItemModel) {
        binding.advertisement = item
    }
}

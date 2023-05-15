package woowacourse.movie.view.activities.home.fragments.screeninglist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.AdvertisementUIState

class AdvertisementItemViewHolder(private val binding: ItemAdvertisementBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(advertisementUIState: AdvertisementUIState) {
        binding.advertisementIv.setImageResource(advertisementUIState.advertisementImage)
    }
}

package woowacourse.movie.view.movies.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.view.movies.model.UiModel.AdvertiseUiModel

class AdvertiseViewHolder(
    private val binding: AdvertisementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AdvertiseUiModel) {
        binding.model = item
    }
}

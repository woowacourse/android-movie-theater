package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.domain.model.Advertisement

class AdViewHolder(
    val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Advertisement) {
        binding.advertisement = item
    }
}

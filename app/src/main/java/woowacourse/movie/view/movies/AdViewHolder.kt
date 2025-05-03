package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.domain.model.Advertisement

class AdViewHolder(
    private val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Advertisement) {
        binding.ad.setImageResource(item.id)
    }
}

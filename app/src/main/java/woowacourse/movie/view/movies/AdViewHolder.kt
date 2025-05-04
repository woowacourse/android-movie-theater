package woowacourse.movie.view.movies

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdViewHolder(
    val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Advertisement) {
        binding.ad.setImageResource(item.id)
    }
}

package woowacourse.movie.view.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.domain.model.Advertisement

class AdViewHolder(
    val parent: ViewGroup,
    val binding: ItemAdvertisementBinding = inflate(parent),
) : MovieListViewHolder(binding.root) {
    fun bind(item: Advertisement) {
        binding.advertisement = item
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemAdvertisementBinding {
            return ItemAdvertisementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}

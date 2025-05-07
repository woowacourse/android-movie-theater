package woowacourse.movie.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.view.home.MovieType.AdvertisementItem

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding,
    private val advertisementClickListener: (url: String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AdvertisementItem) {
        binding.root.setOnClickListener {
            advertisementClickListener.invoke(item.url)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            advertisementClickListener: (url: String) -> Unit,
        ): AdvertisementViewHolder =
            AdvertisementViewHolder(
                ItemAdvertisementBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                advertisementClickListener,
            )
    }
}

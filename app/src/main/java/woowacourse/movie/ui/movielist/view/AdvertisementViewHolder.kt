package woowacourse.movie.ui.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.domain.model.item.Advertisement

class AdvertisementViewHolder(
    private val itemBinding: AdvertisementItemBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: Advertisement) {
        itemBinding.advertisement = item
    }

    companion object {
        fun from(parent: ViewGroup): AdvertisementViewHolder {
            val binding =
                AdvertisementItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AdvertisementViewHolder(binding)
        }
    }
}

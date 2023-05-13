package woowacourse.movie.movielist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.dto.AdDto

class AdViewHolder(private val binding: AdItemBinding, private val onAdClickListener: OnClickListener<AdDto>) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AdDto) {
        binding.ad.setImageResource(item.adImage)
        binding.ad.setOnClickListener {
            onAdClickListener.onClick(item)
        }
    }
}

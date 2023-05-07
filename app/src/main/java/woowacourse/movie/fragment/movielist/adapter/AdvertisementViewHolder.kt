package woowacourse.movie.fragment.movielist.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(binding: ItemAdvertisementBinding, onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }
}

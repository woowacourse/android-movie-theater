package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.uimodel.MovieListModel

class AdItemViewHolder(
    private val binding: ItemAdBinding,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.itemBanner.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bind(ad: MovieListModel.AdModel) {
        binding.itemBanner.setImageResource(ad.banner)
    }
}

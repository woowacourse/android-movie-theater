package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.uimodel.MovieListModel

class AdItemViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false),
) {
    private val binding = ItemAdBinding.bind(itemView)

    init {
        binding.itemBanner.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bind(ad: MovieListModel.AdModel) {
        binding.itemBanner.setImageResource(ad.banner)
    }
}

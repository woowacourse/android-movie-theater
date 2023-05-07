package woowacourse.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.TheaterItemModel
import woowacourse.movie.databinding.ItemTheaterBinding

class TheaterViewHolder(private val binding: ItemTheaterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TheaterItemModel) {
        binding.theaters = item
    }
}

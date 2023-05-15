package woowacourse.movie.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.model.itemmodel.TheaterItemModel
import woowacourse.movie.databinding.ItemTheaterBinding

class TheaterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_theater, parent, false)
) {
    private val binding = ItemTheaterBinding.bind(itemView)

    fun bind(item: TheaterItemModel) {
        binding.theaters = item
    }
}

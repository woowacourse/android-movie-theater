package woowacourse.movie.feature.movieList.viewHolder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.movieList.itemModel.CommonItemModel

abstract class CommonItemViewHolder(protected val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(itemModel: CommonItemModel)
}

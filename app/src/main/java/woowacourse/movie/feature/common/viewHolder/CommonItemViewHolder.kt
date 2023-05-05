package woowacourse.movie.feature.common.viewHolder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.common.itemModel.CommonItemModel

abstract class CommonItemViewHolder(protected val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(itemModel: CommonItemModel)
}

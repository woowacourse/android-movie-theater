package woowacourse.movie.view.base

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<ITEM : BaseViewHolderItem, B : ViewBinding>(
    protected val binding: B,
) : RecyclerView.ViewHolder(binding.root) {
    protected lateinit var item: ITEM

    @CallSuper
    open fun bind(item: ITEM) {
        this.item = item
    }
}

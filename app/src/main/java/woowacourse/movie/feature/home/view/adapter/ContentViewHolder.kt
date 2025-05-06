package woowacourse.movie.feature.home.view.adapter

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

sealed class ContentViewHolder<ITEM : ContentItem, BINDING : ViewDataBinding>(
    protected val binding: BINDING,
) : RecyclerView.ViewHolder(binding.root) {
    protected lateinit var item: ContentItem

    @CallSuper
    open fun bind(item: ITEM) {
        this.item = item
    }
}

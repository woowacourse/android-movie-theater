package woowacourse.movie.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, B : ViewBinding>(
    protected val binding: B,
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}

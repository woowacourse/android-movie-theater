package woowacourse.movie.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.item.ListItem

open class BaseViewHolder(
    parent: ViewGroup,
    layoutId: Int,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false),
) {
    open fun bind(item: ListItem) {}
}

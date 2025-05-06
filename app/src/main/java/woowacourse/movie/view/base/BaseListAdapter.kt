package woowacourse.movie.view.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<ITEM : BaseViewHolderItem, VH : BaseViewHolder<ITEM, ViewBinding>>(
    diffCallback: DiffUtil.ItemCallback<ITEM> =
        object : DiffUtil.ItemCallback<ITEM>() {
            override fun areItemsTheSame(
                oldItem: ITEM,
                newItem: ITEM,
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: ITEM,
                newItem: ITEM,
            ): Boolean = oldItem == newItem
        },
) : ListAdapter<ITEM, VH>(diffCallback) {
    override fun onBindViewHolder(
        holder: VH,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType
}

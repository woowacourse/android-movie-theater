package woowacourse.movie.feature.home.view.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter

class ContentsAdapter(
    private val handler: Handler,
) : ListAdapter<ContentItem, ContentViewHolder<ContentItem, ViewDataBinding>>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContentViewHolder<ContentItem, ViewDataBinding> =
        when (ContentItemViewType.entries[viewType]) {
            ContentItemViewType.MOVIE -> MovieViewHolder(parent, handler)
            ContentItemViewType.ADVERTISEMENT -> AdvertisementViewHolder(parent)
        } as ContentViewHolder<ContentItem, ViewDataBinding>

    override fun onBindViewHolder(
        holder: ContentViewHolder<ContentItem, ViewDataBinding>,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun submitList(list: List<ContentItem?>?) {
        if (itemCount + (list?.size ?: 0) > MAX_ITEM_COUNT) return else super.submitList(list)
    }

    interface Handler : MovieViewHolder.Handler

    companion object {
        private const val MAX_ITEM_COUNT = 10000
    }
}

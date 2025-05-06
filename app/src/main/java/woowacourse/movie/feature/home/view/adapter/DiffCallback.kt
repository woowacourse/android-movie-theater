package woowacourse.movie.feature.home.view.adapter

import androidx.recyclerview.widget.DiffUtil

object DiffCallback : DiffUtil.ItemCallback<ContentItem>() {
    override fun areItemsTheSame(
        oldContentItem: ContentItem,
        newContentItem: ContentItem,
    ): Boolean = oldContentItem.id == newContentItem.id && oldContentItem::class == newContentItem::class

    override fun areContentsTheSame(
        oldContentItem: ContentItem,
        newContentItem: ContentItem,
    ): Boolean = oldContentItem == newContentItem
}

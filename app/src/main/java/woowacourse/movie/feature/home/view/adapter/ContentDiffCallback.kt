package woowacourse.movie.feature.home.view.adapter

import androidx.recyclerview.widget.DiffUtil

object ContentDiffCallback : DiffUtil.ItemCallback<ContentItem>() {
    override fun areItemsTheSame(
        oldContentItem: ContentItem,
        newContentItem: ContentItem,
    ): Boolean = oldContentItem.id == newContentItem.id

    override fun areContentsTheSame(
        oldContentItem: ContentItem,
        newContentItem: ContentItem,
    ): Boolean = oldContentItem == newContentItem
}

package woowacourse.movie.view.model

import woowacourse.movie.view.base.BaseViewHolderItem

sealed class MainItem(
    itemViewType: ItemViewType,
) : BaseViewHolderItem {
    override val viewType: Int = itemViewType.ordinal

    enum class ItemViewType {
        MOVIE_ITEM,
        AD_ITEM,
    }
}

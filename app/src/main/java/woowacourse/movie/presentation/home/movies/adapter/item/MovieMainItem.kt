package woowacourse.movie.presentation.home.movies.adapter.item

import woowacourse.movie.presentation.common.base.BaseViewHolderItem

sealed class MovieMainItem(
    itemViewType: MovieViewType,
) : BaseViewHolderItem {
    override val viewType: Int = itemViewType.ordinal

    enum class MovieViewType {
        MOVIE,
        AD,
    }
}

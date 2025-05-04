package woowacourse.movie.presentation.view.home.movies.adapter.item

import woowacourse.movie.presentation.base.BaseViewHolderItem

sealed class MovieMainItem(
    itemViewType: MovieViewType,
) : BaseViewHolderItem {
    override val viewType: Int = itemViewType.ordinal

    enum class MovieViewType {
        MOVIE,
        AD,
    }
}

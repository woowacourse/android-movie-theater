package woowacourse.movie.presentation.home.movies.adapter.item

import androidx.annotation.DrawableRes
import woowacourse.movie.presentation.common.base.BaseViewHolderItem
import woowacourse.movie.presentation.common.model.MovieUiModel

sealed class MovieMainItem(
    itemViewType: MovieViewType,
) : BaseViewHolderItem {
    override val viewType: Int = itemViewType.ordinal

    data class MovieItem(val movie: MovieUiModel, override val id: Long = movie.id.toLong()) :
        MovieMainItem(MovieViewType.MOVIE)

    data class AdItem(@DrawableRes val resId: Int, override val id: Long = resId.toLong()) :
        MovieMainItem(MovieViewType.AD)

    enum class MovieViewType {
        MOVIE,
        AD,
    }
}

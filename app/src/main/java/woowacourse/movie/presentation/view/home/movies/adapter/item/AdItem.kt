package woowacourse.movie.presentation.view.home.movies.adapter.item

import androidx.annotation.DrawableRes

data class AdItem(
    @DrawableRes val resId: Int,
) : MovieMainItem(MovieViewType.AD) {
    override val id: Long = resId.toLong()
}

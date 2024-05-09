package woowacourse.movie.model.ui

import androidx.annotation.DrawableRes
import woowacourse.movie.R

data class Ad(
    @DrawableRes val poster: Int = R.drawable.ad_img,
) : MovieItem(ITEM_VIEW_TYPE_AD) {
    companion object {
        const val ITEM_VIEW_TYPE_AD: Int = 1
    }
}

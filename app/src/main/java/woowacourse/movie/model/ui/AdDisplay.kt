package woowacourse.movie.model.ui

import androidx.annotation.DrawableRes
import woowacourse.movie.R

data class AdItemDisplay(@DrawableRes val poster: Int = R.drawable.ad_img) : MovieDisplay(ITEM_VIEW_TYPE_AD) {
    companion object {
        const val ITEM_VIEW_TYPE_AD: Int = 1
    }
}

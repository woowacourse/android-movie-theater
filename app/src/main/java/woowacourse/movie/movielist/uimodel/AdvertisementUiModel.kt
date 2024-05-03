package woowacourse.movie.movielist.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.model.Advertisement

data class AdvertisementUiModel(
    @DrawableRes val image: Int = R.drawable.img_advertisement,
) : ListItemUiModel {
    constructor(advertisement: Advertisement) : this() {
        // It's fake advertisement, It does Nothing.
    }
}

package woowacourse.movie.movielist.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.model.Advertisement

data class AdvertisementUiModel(
    @DrawableRes val image: Int = R.drawable.img_advertisement,
) : ListItemUiModel {
    constructor(advertisement: Advertisement) : this() {
        // 가짜 광고, 실제 무언가를 표기하지 않음.
    }
}

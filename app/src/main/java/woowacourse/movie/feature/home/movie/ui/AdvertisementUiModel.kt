package woowacourse.movie.feature.home.movie.ui

import androidx.annotation.DrawableRes

class AdvertisementUiModel(
    @DrawableRes val advertisementImageId: Int,
    val advertisementLink: String,
    val advertisementClick: (String) -> Unit,
)

package woowacourse.movie.model

import androidx.annotation.DrawableRes

data class AdvertisementUiModel(
    @DrawableRes val image: Int,
    val url: String,
) : UiModel

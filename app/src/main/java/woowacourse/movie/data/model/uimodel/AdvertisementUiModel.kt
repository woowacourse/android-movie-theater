package woowacourse.movie.data.model.uimodel

import androidx.annotation.DrawableRes

data class AdvertisementUiModel(
    @DrawableRes val image: Int,
    val url: String,
) : UiModel

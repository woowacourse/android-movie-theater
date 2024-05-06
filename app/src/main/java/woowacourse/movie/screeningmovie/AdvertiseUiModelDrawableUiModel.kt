package woowacourse.movie.screeningmovie

import androidx.annotation.DrawableRes
import woowacourse.movie.R

interface AdvertiseUiModel : ScreeningItem

data class AdvertiseUiModelDrawableUiModel(
    @DrawableRes val image: Int = R.drawable.img_advertisement,
) : AdvertiseUiModel

data class AdvertiseUiModelUrlUiModel(
    val imageUrl: String = "",
) : AdvertiseUiModel

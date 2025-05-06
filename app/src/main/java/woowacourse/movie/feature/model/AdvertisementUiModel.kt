package woowacourse.movie.feature.model

import androidx.annotation.DrawableRes
import woowacourse.movie.R

data class AdvertisementUiModel(
    override val id: Long = 0L,
    @DrawableRes val image: Int = R.drawable.img_default_advertisement,
) : ContentUiModel(id)

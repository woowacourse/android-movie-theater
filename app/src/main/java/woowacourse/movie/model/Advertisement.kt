package woowacourse.movie.model

import androidx.annotation.DrawableRes

data class Advertisement(
    @DrawableRes
    val banner: Int,
    val link: String,
)

package woowacourse.movie.model

import androidx.annotation.DrawableRes
import com.woowacourse.domain.Ad
import com.woowacourse.domain.ViewType

fun AdUIModel.toDomain() = Ad(adImage, url, viewType)
fun Ad.toPresentation() = AdUIModel(adImage, url, viewType)

data class AdUIModel(
    @DrawableRes
    val adImage: Int,
    val url: String,
    val viewType: ViewType = ViewType.AD,
)

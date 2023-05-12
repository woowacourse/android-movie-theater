package woowacourse.movie.dto.movie

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.fragment.home.recyclerview.ViewType
import java.io.Serializable

data class AdUIModel(
    val viewType: ViewType = ViewType.AD_VIEW,
    @DrawableRes val adImage: Int,
    val url: String,
) : Serializable {
    companion object {
        fun getAdData(): AdUIModel {
            return AdUIModel(
                adImage = R.drawable.ad,
                url = "https://woowacourse.github.io/",
            )
        }
    }
}

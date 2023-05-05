package woowacourse.movie.model

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.fragment.movielist.ViewType

data class Ad(
    @DrawableRes
    val adImage: Int,
    val url: String,
    val viewType: ViewType = ViewType.AD,
) {
    companion object {
        val dummyAd = Ad(R.drawable.ad, "https://woowacourse.github.io/")
    }
}

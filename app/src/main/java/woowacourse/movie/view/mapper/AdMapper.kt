package woowacourse.movie.view.mapper

import woowacourse.movie.R
import woowacourse.movie.domain.Ad
import woowacourse.movie.view.model.MovieListModel

val adMapper: Map<Int, Int> = mapOf(
    1 to R.drawable.woowacourse_banner
)

fun Ad.toUiModel(): MovieListModel.MovieAdModel? {
    val bannerResource = adMapper[bannerId]
    return bannerResource?.let {
        MovieListModel.MovieAdModel(
            bannerResource,
            url
        )
    }
}

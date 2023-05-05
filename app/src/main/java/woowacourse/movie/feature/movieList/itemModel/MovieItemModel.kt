package woowacourse.movie.feature.movieList.itemModel

import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.MovieState

class MovieItemModel(
    val movieState: MovieState,
    val onClick: (movieState: MovieState) -> Unit
) : CommonItemModel {
    override val viewType: CommonViewType = CommonViewType.MOVIE
}

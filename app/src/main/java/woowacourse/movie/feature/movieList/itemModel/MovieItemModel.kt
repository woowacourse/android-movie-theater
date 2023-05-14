package woowacourse.movie.feature.movieList.itemModel

import woowacourse.movie.feature.movieList.CommonViewType
import woowacourse.movie.model.MovieState

class MovieItemModel(
    val movieState: MovieState,
    val onClick: (movieState: MovieState) -> Unit
) : CommonItemModel {
    override val viewType: CommonViewType = CommonViewType.MOVIE
}

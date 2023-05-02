package woowacourse.movie.feature.movieList.itemModel

import woowacourse.movie.feature.common.ViewType
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.MovieState

class MovieItemModel(
    val movieState: MovieState,
    val onClick: (movieState: MovieState) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}

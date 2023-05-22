package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.model.MovieState

class MovieItemModel(
    val movieState: MovieState,
    val onClick: (movieState: MovieState) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}

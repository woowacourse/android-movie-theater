package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.MovieState

class MovieItemModel(
    val movieState: MovieState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}

package woowacourse.movie.ui.adapter.itemModel

import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adapter.itemModel.ItemModel.Companion.TYPE_MOVIE

class MovieItemModel(
    val movieState: MovieState
) : ItemModel {
    override val viewType: Int
        get() = TYPE_MOVIE
}

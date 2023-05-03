package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.MovieState

class MovieItemModel(
    val movieState: MovieState
) : ItemModel {
    companion object {
        const val type: Int = 1
    }
}

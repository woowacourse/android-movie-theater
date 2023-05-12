package woowacourse.movie.ui.adapter.itemModel

import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adapter.itemModel.ItemModel.Companion.TYPE_CINEMA

class CinemaItemModel(
    val cinema: CinemaState,
    val movie: MovieState
) : ItemModel {
    override val viewType: Int
        get() = TYPE_CINEMA
}

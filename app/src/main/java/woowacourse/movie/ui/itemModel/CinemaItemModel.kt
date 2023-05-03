package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState

class CinemaItemModel(
    val cinema: CinemaState,
    val movie: MovieState
) : ItemModel {
    companion object {
        const val type: Int = 4
    }
}

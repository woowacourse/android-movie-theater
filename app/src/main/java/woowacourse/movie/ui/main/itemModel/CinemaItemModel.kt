package woowacourse.movie.ui.main.itemModel

import woowacourse.movie.model.CinemaState
import woowacourse.movie.ui.main.ViewType

class CinemaItemModel(
    val cinema: CinemaState
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE

    companion object {
        val type: Int = 1
    }
}

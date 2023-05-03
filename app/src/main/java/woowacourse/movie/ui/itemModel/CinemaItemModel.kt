package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.CinemaState

class CinemaItemModel(
    val cinema: CinemaState
) : ItemModel {
    companion object {
        const val type: Int = 4
    }
}

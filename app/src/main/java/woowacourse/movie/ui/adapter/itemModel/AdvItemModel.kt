package woowacourse.movie.ui.adapter.itemModel

import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState
) : ItemModel {
    companion object {
        const val type: Int = 2
    }
}

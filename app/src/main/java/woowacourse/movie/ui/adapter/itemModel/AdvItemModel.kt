package woowacourse.movie.ui.adapter.itemModel

import woowacourse.movie.model.AdvState
import woowacourse.movie.ui.adapter.itemModel.ItemModel.Companion.TYPE_AD

class AdvItemModel(
    val advState: AdvState
) : ItemModel {
    override val viewType: Int
        get() = TYPE_AD
}

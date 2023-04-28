package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType = ViewType.ADV
}

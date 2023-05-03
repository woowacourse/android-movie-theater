package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType = ViewType.ADV
}

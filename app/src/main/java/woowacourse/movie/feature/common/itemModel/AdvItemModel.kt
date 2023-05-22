package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    val onClick: (advState: AdvState) -> Unit
) : ItemModel {
    override val viewType = ViewType.ADV
}

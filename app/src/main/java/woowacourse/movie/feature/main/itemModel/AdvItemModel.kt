package woowacourse.movie.feature.main.itemModel

import woowacourse.movie.feature.main.ViewType
import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType = ViewType.ADV
}

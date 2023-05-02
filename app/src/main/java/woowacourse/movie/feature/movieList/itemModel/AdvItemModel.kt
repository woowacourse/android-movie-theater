package woowacourse.movie.feature.movieList.itemModel

import woowacourse.movie.feature.common.ViewType
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    val onClick: (advState: AdvState) -> Unit
) : ItemModel {
    override val viewType = ViewType.ADV
}

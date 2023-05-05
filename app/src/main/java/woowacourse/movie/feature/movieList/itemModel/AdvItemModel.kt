package woowacourse.movie.feature.movieList.itemModel

import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    val onClick: (advState: AdvState) -> Unit
) : CommonItemModel {
    override val viewType = CommonViewType.ADV
}

package woowacourse.movie.feature.movieList.itemModel

import woowacourse.movie.feature.movieList.CommonViewType
import woowacourse.movie.model.AdvState

class AdvItemModel(
    val advState: AdvState,
    val onClick: (advState: AdvState) -> Unit
) : CommonItemModel {
    override val viewType = CommonViewType.ADV
}

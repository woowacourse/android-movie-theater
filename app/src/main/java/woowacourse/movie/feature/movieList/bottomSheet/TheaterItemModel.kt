package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.TheaterScreeningState

data class TheaterItemModel(
    val theater: TheaterScreeningState,
    val onClick: (TheaterScreeningState) -> Unit
) : CommonItemModel {
    override val viewType: CommonViewType = CommonViewType.THEATER
}

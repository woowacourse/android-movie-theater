package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.TheaterScreeningInfoState

data class TheaterItemModel(
    val theaterScreening: TheaterScreeningInfoState,
    val onClick: (TheaterScreeningInfoState) -> Unit
) : CommonItemModel {
    override val viewType: CommonViewType = CommonViewType.THEATER
}

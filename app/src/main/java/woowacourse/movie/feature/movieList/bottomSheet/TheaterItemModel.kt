package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.TheaterState

data class TheaterItemModel(
    val theater: TheaterState,
    val onClick: (TheaterState) -> Unit
) : CommonItemModel {
    override val viewType: CommonViewType = CommonViewType.THEATER
}

package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.model.TheaterScreeningInfoState

data class TheaterItemModel(
    val theaterScreening: TheaterScreeningInfoState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.THEATER
}

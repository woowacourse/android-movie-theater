package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.model.TheaterScreeningInfoState

data class TheaterItemModel(
    val theaterScreening: TheaterScreeningInfoState,
    val onClick: (theaterScreeningInfoState: TheaterScreeningInfoState) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.THEATER
}

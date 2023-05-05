package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.feature.common.ViewType
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.TheaterState

data class TheaterItemModel(
    val theater: TheaterState,
    val onClick: (TheaterState) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.THEATER
}

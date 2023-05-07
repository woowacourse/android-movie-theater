package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.TheaterUiModel

data class TheaterItemModel(
    val theaterUiModel: TheaterUiModel,
    val onClick: (TheaterUiModel) -> Unit
)

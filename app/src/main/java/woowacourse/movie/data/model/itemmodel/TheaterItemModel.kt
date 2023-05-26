package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.TheaterUIModel

data class TheaterItemModel(
    val theaterUiModel: TheaterUIModel,
    val onClick: (TheaterUIModel) -> Unit
)

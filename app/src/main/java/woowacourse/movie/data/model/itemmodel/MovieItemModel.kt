package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.MovieUiModel

data class MovieItemModel(
    val movie: MovieUiModel,
    val onClick: (MovieUiModel) -> Unit
)

package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.MovieUIModel

data class MovieItemModel(
    val movie: MovieUIModel,
    val onClick: (MovieUIModel) -> Unit
)

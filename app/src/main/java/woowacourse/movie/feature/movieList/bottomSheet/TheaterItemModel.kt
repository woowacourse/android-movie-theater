package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.model.TheaterState

data class TheaterItemModel(
    val theater: TheaterState,
    val onClick: (TheaterState) -> Unit
)

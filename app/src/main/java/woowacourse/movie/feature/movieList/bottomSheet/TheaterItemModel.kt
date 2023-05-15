package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.model.TheaterScreeningInfoState

data class TheaterItemModel(
    val theaterScreening: TheaterScreeningInfoState,
    val onClick: (TheaterScreeningInfoState) -> Unit
)

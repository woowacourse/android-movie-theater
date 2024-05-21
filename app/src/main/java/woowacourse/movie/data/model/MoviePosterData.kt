package woowacourse.movie.data.model

import woowacourse.movie.domain.model.Image

data class MoviePosterData(
    val movieId: Int,
    val poster: Image<Any>,
)

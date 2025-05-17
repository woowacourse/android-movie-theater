package woowacourse.movie.data.mapper

import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.domain.model.movie.Movie

object MovieMapper {
    fun toModel(movieEntity: MovieEntity): Movie =
        with(movieEntity) {
            Movie(
                title,
                posterId,
                screeningPeriod,
                runningTime,
            )
        }
}

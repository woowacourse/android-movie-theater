package woowacourse.app.data.movie

import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.ScreeningPeriod

object MovieMapper {
    fun MovieEntity.toMovie(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            screeningPeriod = ScreeningPeriod(this.startDate, this.endDate),
            runningTime = this.runningTime,
            description = this.description,
        )
    }

    fun Movie.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title,
            startDate = this.screeningPeriod.startDate.value,
            endDate = this.screeningPeriod.endDate.value,
            runningTime = this.runningTime,
            description = this.description,
        )
    }
}

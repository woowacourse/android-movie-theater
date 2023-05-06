package woowacourse.app.presentation.model.movie

import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.ScreeningPeriod
import woowacourse.movie.R

object MovieMapper {

    private val thumbnailMap: Map<Long, Int> = mapOf(
        1L to R.drawable.harry_potter_thumbnail,
        2L to R.drawable.iron_man_thumbnail,
        3L to R.drawable.suzume_thumbnail,
    )

    private val posterMap: Map<Long, Int> = mapOf(
        1L to R.drawable.harry_potter_poster,
        2L to R.drawable.iron_man_poster,
        3L to R.drawable.suzume_poster,
    )

    fun getThumbnail(id: Long): Int {
        return thumbnailMap[id] ?: throw IllegalArgumentException("없는 아이디 입니다")
    }

    fun getPoster(id: Long): Int {
        return posterMap[id] ?: throw IllegalArgumentException("없는 아이디 입니다")
    }

    fun Movie.toUiModel(): MovieUiModel {
        return MovieUiModel(
            id = this.id,
            title = this.title,
            startDate = this.screeningPeriod.startDate.value,
            endDate = this.screeningPeriod.endDate.value,
            screeningDates = this.screeningDates,
            runningTime = this.runningTime,
            description = this.description,
            thumbnail = getThumbnail(this.id),
            poster = getPoster(this.id),
        )
    }

    fun MovieUiModel.toDomainModel(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            screeningPeriod = ScreeningPeriod(this.startDate, this.endDate),
            runningTime = this.runningTime,
            description = this.description,
        )
    }

    fun List<Movie>.toUiMovies(): List<MovieUiModel> = map { it.toUiModel() }
}

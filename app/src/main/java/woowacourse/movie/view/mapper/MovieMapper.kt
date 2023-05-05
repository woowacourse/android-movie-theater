package woowacourse.movie.view.mapper

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.view.model.MovieUiModel

fun Movie.toUiModel(@DrawableRes posterResourceId: Int): MovieUiModel {
    val convertSchedule = buildMap {
        schedule.schedule.forEach { (name, screeningDateTimes) ->
            put(name, screeningDateTimes.dateTimes)
        }
    }

    return MovieUiModel(
        title,
        startDate,
        endDate,
        runningTime.value,
        posterResourceId,
        summary,
        convertSchedule,
    )
}

// fun MovieUiModel.toDomainModel(): Movie = Movie(
//     title,
//     ScreeningDateTimes(screeningDateTimes),
//     Minute(runningTime),
//     summary
// )

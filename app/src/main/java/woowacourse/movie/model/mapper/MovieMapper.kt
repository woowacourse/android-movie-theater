package woowacourse.movie.model.mapper

import domain.DateRange
import domain.Movie
import woowacourse.movie.model.MovieUiModel

object MovieMapper : DomainViewMapper<Movie, MovieUiModel> {
    override fun MovieUiModel.toDomain(): Movie {
        return Movie(
            imagePath = picture.toString(),
            title = title,
            date = DateRange(startDate, endDate),
            runningTime = runningTime,
            description = description
        )
    }

    override fun Movie.toUi(): MovieUiModel {
        return MovieUiModel(
            picture = imagePath.toInt(),
            title = title,
            startDate = date.startDate,
            endDate = date.endDate,
            runningTime = runningTime,
            description = description
        )
    }
}

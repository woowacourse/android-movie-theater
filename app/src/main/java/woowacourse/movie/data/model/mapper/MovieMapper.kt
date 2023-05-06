package woowacourse.movie.data.model.mapper

import domain.DateRange
import domain.Movie
import woowacourse.movie.data.model.uimodel.MovieUiModel

object MovieMapper : DomainViewMapper<Movie, MovieUiModel> {
    override fun toDomain(movieUiModel: MovieUiModel): domain.Movie {
        return Movie(
            imagePath = movieUiModel.picture.toString(),
            title = movieUiModel.title,
            date = DateRange(movieUiModel.startDate, movieUiModel.endDate),
            runningTime = movieUiModel.runningTime,
            description = movieUiModel.description
        )
    }

    override fun toUi(movie: Movie): MovieUiModel {
        return MovieUiModel(
            picture = movie.imagePath.toInt(),
            title = movie.title,
            startDate = movie.date.startDate,
            endDate = movie.date.endDate,
            runningTime = movie.runningTime,
            description = movie.description
        )
    }
}

package woowacourse.movie.data.model.mapper

import domain.DateRange
import domain.Movie
import domain.Movies
import woowacourse.movie.data.model.uimodel.MovieUIModel

object MovieMapper : DomainViewMapper<Movie, MovieUIModel> {
    override fun toDomain(movieUIModel: MovieUIModel): domain.Movie {
        return Movie(
            imagePath = movieUIModel.picture.toString(),
            title = movieUIModel.title,
            date = DateRange(movieUIModel.startDate, movieUIModel.endDate),
            runningTime = movieUIModel.runningTime,
            description = movieUIModel.description
        )
    }

    override fun toUI(movie: Movie): MovieUIModel {
        return MovieUIModel(
            picture = movie.imagePath.toInt(),
            title = movie.title,
            startDate = movie.date.startDate,
            endDate = movie.date.endDate,
            runningTime = movie.runningTime,
            description = movie.description
        )
    }

    fun toUi(movies: Movies): List<MovieUIModel> {
        return movies.value.map {
            movie ->
            toUI(movie)
        }
    }
}

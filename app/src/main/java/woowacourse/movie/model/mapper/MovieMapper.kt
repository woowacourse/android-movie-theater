package woowacourse.movie.model.mapper


import domain.Movie
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.mapper.TheatersMapper.toDomain
import woowacourse.movie.model.mapper.TheatersMapper.toUi

object MovieMapper : DomainViewMapper<Movie, MovieUiModel> {
    override fun MovieUiModel.toDomain(): Movie {
        return Movie(
            imagePath = picture.toString(),
            title = title,
            theaters = theaters.toDomain(),
            runningTime = runningTime,
            description = description
        )
    }

    override fun Movie.toUi(): MovieUiModel {
        return MovieUiModel(
            picture = imagePath.toInt(),
            title = title,
            startDate = theaters.getStartDate(),
            endDate = theaters.getEndDate(),
            theaters = theaters.toUi(),
            runningTime = runningTime,
            description = description
        )
    }
}

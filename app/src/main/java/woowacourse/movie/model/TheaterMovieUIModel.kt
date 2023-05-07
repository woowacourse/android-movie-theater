package woowacourse.movie.model

import com.woowacourse.domain.TheaterMovie
import java.io.Serializable

fun TheaterMovie.toPresentation() = TheaterMovieUIModel(name, movieInfo.toPresentation())
fun TheaterMovieUIModel.toDomain() = TheaterMovie(name, movieInfo.toDomain())

data class TheaterMovieUIModel(
    val name: String,
    val movieInfo: ScreeningScheduleUIModel
) : Serializable

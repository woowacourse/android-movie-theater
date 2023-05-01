package woowacourse.movie.movie.mapper.movie

import domain.movieinfo.Movie
import woowacourse.movie.movie.dto.movie.MovieDto

fun MovieDto.mapToMovie(): Movie {
    return Movie(
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        runningTime = this.runningTime,
        description = this.description,
        moviePoster = this.moviePoster,
    )
}

fun Movie.mapToMovieDto(): MovieDto {
    return MovieDto(
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        runningTime = this.runningTime,
        description = this.description,
        moviePoster = this.moviePoster,
    )
}

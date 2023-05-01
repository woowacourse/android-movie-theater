package woowacourse.movie.movie.mapper.movie

import domain.movieinfo.MovieDate
import woowacourse.movie.movie.dto.movie.MovieDateDto

fun MovieDateDto.mapToMovieDate(): MovieDate {
    return MovieDate(this.date)
}

fun MovieDate.mapToMovieDateDto(): MovieDateDto {
    return MovieDateDto(this.date)
}

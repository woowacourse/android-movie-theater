package woowacourse.movie.mapper.movie

import domain.movieinfo.MovieDate
import woowacourse.movie.movielist.dto.MovieDateDto

fun MovieDateDto.mapToMovieDate(): MovieDate {
    return MovieDate(this.date)
}

fun MovieDate.mapToMovieDateDto(): MovieDateDto {
    return MovieDateDto(this.date)
}

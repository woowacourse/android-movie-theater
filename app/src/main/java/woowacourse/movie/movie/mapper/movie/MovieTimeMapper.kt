package woowacourse.movie.movie.mapper.movie

import domain.movieinfo.MovieTime
import woowacourse.movie.movie.dto.movie.MovieTimeDto

fun MovieTimeDto.mapToMovieTime(): MovieTime {
    return MovieTime(this.time)
}

fun MovieTime.mapToMovieTimeDto(): MovieTimeDto {
    return MovieTimeDto(this.time)
}

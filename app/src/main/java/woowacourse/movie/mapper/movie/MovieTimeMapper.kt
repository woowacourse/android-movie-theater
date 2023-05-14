package woowacourse.movie.mapper.movie

import domain.movieinfo.MovieTime
import woowacourse.movie.movielist.dto.MovieTimeDto

fun MovieTimeDto.mapToMovieTime(): MovieTime {
    return MovieTime(this.time)
}

fun MovieTime.mapToMovieTimeDto(): MovieTimeDto {
    return MovieTimeDto(this.time)
}

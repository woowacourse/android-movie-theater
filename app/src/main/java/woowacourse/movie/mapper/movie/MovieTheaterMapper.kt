package woowacourse.movie.mapper.movie

import domain.movieinfo.MovieTheater
import domain.movieinfo.MovieTheaters
import woowacourse.movie.theater.dto.MovieTheaterDto
import woowacourse.movie.theater.dto.MovieTheatersDto

fun MovieTheaterDto.mapToMovieTheater(): MovieTheater {
    return MovieTheater(this.place, this.time)
}

fun MovieTheater.mapToMovieTheaterDto(): MovieTheaterDto {
    return MovieTheaterDto(this.place, this.time)
}

fun MovieTheatersDto.mapToMovieTheaters(): MovieTheaters {
    return MovieTheaters(this.theaters.map { it.mapToMovieTheater() })
}

fun MovieTheaters.mapToMovieTheatersDto(): MovieTheatersDto {
    return MovieTheatersDto(this.theaters.map { it.mapToMovieTheaterDto() })
}

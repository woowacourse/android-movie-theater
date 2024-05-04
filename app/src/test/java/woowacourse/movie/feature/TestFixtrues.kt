package woowacourse.movie.feature

import woowacourse.movie.data.MovieRepository

val firstMovieId = 0L
val firstMovie = MovieRepository.getMovieById(0L)
val invalidMovieId = -1L

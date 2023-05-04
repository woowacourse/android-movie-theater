package woowacourse.movie.domain

data class Theaters(val value: List<Theater>) {
    fun findTheaterByMovie(movie: Movie): Theaters {
        return value.filter { theater ->
            theater.movieSchedules.find { it.movie.title == movie.title } != null
        }.let { Theaters(it) }
    }
}

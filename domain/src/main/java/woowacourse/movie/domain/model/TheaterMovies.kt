package woowacourse.movie.domain.model

data class TheaterMovies(private val movies: List<TheaterMovie>) {
    fun findMovie(movie: Movie): TheaterMovie? {
        return movies.find {
            it.movie == movie
        }
    }

    fun addMovie(movie: TheaterMovie): List<TheaterMovie> {
        return movies + movie
    }
}

package woowacourse.movie.domain.model

data class Theater private constructor(val name: String, val movies: TheaterMovies) {
    fun findMovie(movie: Movie): TheaterMovie? {
        return movies.findMovie(movie)
    }

    fun addMovie(movie: TheaterMovie) {
        movies.addMovie(movie)
    }

    companion object {
        fun from(name: String, movies: List<MovieRunningTime>): Theater {
            val theaterMovies: TheaterMovies = movies.map {
                TheaterMovie(name, it.movie, it.times)
            }.let { TheaterMovies(it) }
            return Theater(name, theaterMovies)
        }
    }
}

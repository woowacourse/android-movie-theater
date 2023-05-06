package domain

class Movies(val value: List<Movie>) {
    fun findMovieByTitle(movieTitle: String): Movie? {
        return value.find {
            it.title == movieTitle
        }
    }
}

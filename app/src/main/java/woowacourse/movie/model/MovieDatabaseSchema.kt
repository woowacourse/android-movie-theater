package woowacourse.movie.model

interface MovieDatabaseSchema {
    val screenings: Map<String, Map<String, List<Int>>>
    val movies: Map<String, Movie>
}

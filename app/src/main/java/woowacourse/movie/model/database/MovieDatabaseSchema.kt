package woowacourse.movie.model.database

import woowacourse.movie.model.movie.Movie

interface MovieDatabaseSchema {
    val screenings: Map<String, Map<String, List<Int>>>
    val movies: Map<String, Movie>
}

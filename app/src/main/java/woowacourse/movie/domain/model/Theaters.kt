package woowacourse.movie.domain.model

import woowacourse.movie.data.model.MovieData

data class Theaters(val theaters: List<Theater>) {
    constructor(vararg theaters: Theater) : this(theaters.toList())

    fun screeningTheater(movieData: MovieData): Theaters =
        Theaters(
            theaters
                .filter { theater ->
                    theater.screens.any { screen ->
                        screen.movieData.id == movieData.id
                    }
                },
        )
}

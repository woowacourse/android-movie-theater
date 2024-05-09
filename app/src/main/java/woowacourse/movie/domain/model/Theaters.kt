package woowacourse.movie.domain.model

import java.io.Serializable

data class Theaters(val theaters: List<Theater>) : Serializable {
    constructor(vararg theaters: Theater) : this(theaters.toList())

    fun screeningTheater(screen: Screen): Theaters =
        Theaters(
            theaters.filter { theater ->
                theater.screens.any { it.id == screen.id }
            },
        )

    fun screeningTheater(movie: Movie): Theaters =
        Theaters(
            theaters.filter { theater ->
                theater.screens.any { it.id == movie.id }
            },
        )
}

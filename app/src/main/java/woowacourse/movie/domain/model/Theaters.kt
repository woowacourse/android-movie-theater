package woowacourse.movie.domain.model

data class Theaters(val theaters: List<Theater>) {
    constructor(vararg theaters: Theater) : this(theaters.toList())

    fun screeningTheater(screen: Screen): Theaters =
        Theaters(
            theaters.filter { theater ->
                theater.screens.any { it.id == screen.id }
            },
        )
}

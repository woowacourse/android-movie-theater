package woowacourse.movie.domain.model.movie

sealed interface MovieContent {
    data class MovieEntry(
        val movie: Movie,
    ) : MovieContent

    data class MovieAd(
        val id: Int,
    ) : MovieContent
}

package woowacourse.movie.domain.model

interface MovieListItemRule<out T> {
    fun whenIndex(index: Int): Class<out T>
}

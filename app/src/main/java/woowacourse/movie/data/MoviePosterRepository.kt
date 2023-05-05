package woowacourse.movie.data

interface MoviePosterRepository {
    fun findPoster(title: String): Int
}

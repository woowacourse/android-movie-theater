package woowacourse.movie.domain.repository

interface MoviePosterRepository {
    fun findPoster(title: String): Int
}

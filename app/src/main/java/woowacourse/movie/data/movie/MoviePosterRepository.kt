package woowacourse.movie.data.movie

interface MoviePosterRepository {
    fun findPoster(title: String): Int
}

package woowacourse.movie.domain.model

class ScreeningInfos(
    private val value: List<Screening>,
) {
    fun findByMovie(movie: Movie): List<Screening> = value.filter { it.movie == movie }
}

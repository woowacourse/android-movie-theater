package woowacourse.movie.domain.model

data class Theater(
    val id: Int,
    val name: String,
    val screens: List<Screen>,
) {
    fun findScreenSize(movieId: Int): Int {
        return screens.find { it.movie?.id == movieId }?.selectableDates?.size ?: 0
    }
}

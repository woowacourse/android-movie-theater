package woowacourse.movie.domain.model.theater

class Theater(
    val name: String,
    val movieSchedules: List<Screening>,
) {
    fun screeningTimeCount(movieId: Int) = movieSchedules.count { it.movieId == movieId }
}

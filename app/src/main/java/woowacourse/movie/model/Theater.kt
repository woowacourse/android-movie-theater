package woowacourse.movie.model

class Theater(
    val place: String,
    val schedules: List<Schedule>,
) {
    fun reservableTimeCount(movie: Movie): Int {
        return schedules.find { it.movie == movie }?.screeningTimes?.size ?: 0
    }
}

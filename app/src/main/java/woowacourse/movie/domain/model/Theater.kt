package woowacourse.movie.domain.model

data class Theater(
    val id: Int,
    val name: String,
    val screens: List<Screen>,
) {
    fun findScreenTimeCount(movieId: Int): Int {
        val screen = screens.find { it.movie.id == movieId } ?: return 0
        return screen.selectableDates.sumOf { it.getSelectableTimes().size }
    }
}

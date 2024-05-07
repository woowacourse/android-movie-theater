package woowacourse.movie.domain.model

import java.io.Serializable

data class Theater(
    val id: Int,
    val name: String,
    val screens: List<Screen>,
) : Serializable {
    fun isScreening(screen: Screen): Boolean = screens.contains(screen)

    fun allScreeningTimeCount(screen: Screen): Int = screen.allScreeningTime(WeeklyScreenTimePolicy()).count()
}

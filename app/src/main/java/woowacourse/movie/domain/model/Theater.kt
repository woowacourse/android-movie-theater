package woowacourse.movie.domain.model

import woowacourse.movie.data.model.ScreenData

data class Theater(
    val id: Int,
    val name: String,
    val screens: List<ScreenData>,
) {
    fun hasScreen(screenData: ScreenData): Boolean = screens.contains(screenData)

    fun allScreeningTimeCount(screenData: ScreenData): Int = screenData.allScreeningTime(WeeklyScreenTimePolicy()).count()

    companion object {
        val NULL = Theater(0, "", emptyList())
    }
}

package woowacourse.movie.model.theater

data class Theater(
    val theaterId: Int,
    val name: String,
    val screeningSchedule: List<ScreeningSlot>,
)

package woowacourse.movie.model.theater

data class Theater(
    val theaterId: Int,
    val name: String,
    val screeningSchedule: List<ScreeningSlot>,
) {
    companion object {
        const val DEFAULT_THEATER_ID = -1
    }
}

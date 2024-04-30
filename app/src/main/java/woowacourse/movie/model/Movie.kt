package woowacourse.movie.model

data class Movie(
    val id: Long,
    val title: String,
    val thumbnail: Int,
    val screeningDates: ScreeningDates,
    val runningTime: Int,
    val introduction: String,
) {
    fun screenPeriodToString(): String {
        return "${screeningDates.startDate} ~ ${screeningDates.endDate}"
    }
}

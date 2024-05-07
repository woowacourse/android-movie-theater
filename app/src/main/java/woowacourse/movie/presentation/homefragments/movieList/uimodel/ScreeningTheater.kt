package woowacourse.movie.presentation.homefragments.movieList.uimodel

data class ScreeningTheater(
    val movieId: Long,
    val theaterId: Long,
    val theaterName: String,
    val numberOfScreeningSchedule: Int,
)

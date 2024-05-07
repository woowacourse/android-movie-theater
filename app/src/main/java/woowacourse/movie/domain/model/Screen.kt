package woowacourse.movie.domain.model

data class Screen(
    val screenId: Int,
    val movie: ScreenView.Movie,
    val selectableDates: List<ScreenDate>,
)

package woowacourse.movie.domain.model

data class Screen(
    val id: Int,
    val movie: ScreenView.Movie,
    val selectableDates: List<ScreenDate>,
)

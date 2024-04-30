package woowacourse.movie.presentation.ui.screen.bottom

interface BottomTheaterActionHandler {
    fun onTheaterClick(
        movieId: Int,
        theaterId: Int,
    )
}

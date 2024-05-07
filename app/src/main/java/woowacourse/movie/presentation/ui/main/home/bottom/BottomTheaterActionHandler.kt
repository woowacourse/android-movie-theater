package woowacourse.movie.presentation.ui.main.home.bottom

interface BottomTheaterActionHandler {
    fun onTheaterClick(
        movieId: Int,
        theaterId: Int,
    )
}

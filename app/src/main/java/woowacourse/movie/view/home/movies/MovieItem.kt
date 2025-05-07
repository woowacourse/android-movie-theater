package woowacourse.movie.view.home.movies

sealed class MovieItem {
    data class ScreeningMovieUi(val movieUi: MovieUi) : MovieItem()

    data class Advertisement(val adId: Int) : MovieItem()
}

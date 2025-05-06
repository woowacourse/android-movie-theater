package woowacourse.movie.view.home.movies

import woowacourse.movie.domain.AdvertisementId

sealed class MovieItem {
    data class ScreeningMovieUi(val movieUi: MovieUi) : MovieItem()

    data class Advertisement(val adId: AdvertisementId) : MovieItem()
}

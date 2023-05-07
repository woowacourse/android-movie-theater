package woowacourse.movie.repository

import domain.Movies
import woowacourse.movie.data.model.uimodel.AdvertisementUiModel

interface MoviesRepository {
    val movies: Movies
    val advertisements: AdvertisementUiModel
}

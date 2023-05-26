package woowacourse.movie.repository

import domain.Movies
import woowacourse.movie.data.model.uimodel.AdvertisementUIModel

interface MoviesRepository {
    val movies: Movies
    val advertisements: AdvertisementUIModel
}

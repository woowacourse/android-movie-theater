package woowacourse.movie

import woowacourse.movie.db.advertisement.AdvertisementDao
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie

object TestFixture {
    val mockMovies: List<Movie> = ScreeningDao().findAll()
    val mockAdvertisements: List<Advertisement> = AdvertisementDao().findAll()
}

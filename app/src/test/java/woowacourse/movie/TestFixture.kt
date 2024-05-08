package woowacourse.movie

import woowacourse.movie.db.advertisement.AdvertisementDao
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie
import java.time.LocalTime

object TestFixture {
    val mockMovies: List<Movie> = ScreeningDao().findAll()
    val mockAdvertisements: List<Advertisement> = AdvertisementDao().findAll()

    fun getMockScreeningTimes(
        movieId: Int,
        theaterId: Int,
    ): List<LocalTime> = TheaterDao().findScreeningTimesByMovieId(movieId, theaterId)
}

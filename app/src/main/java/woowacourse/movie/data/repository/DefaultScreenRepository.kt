package woowacourse.movie.data.repository

import woowacourse.movie.data.source.ScreenDataSource
import woowacourse.movie.domain.model.ScreenAndAd

class DefaultScreenRepository(
    private val screenDataSource: ScreenDataSource,
    private val movieRepository: MovieRepository,
) : ScreenRepository {
    override fun loadAllScreens(): List<ScreenAndAd.Screen> {
        val allScreenData = screenDataSource.load()
        return allScreenData.map {
            ScreenAndAd.Screen(
                id = it.id,
                movie = movieRepository.loadMovie(it.movieData.id),
                dateRange = it.dateRange,
            )
        }
    }

    override fun loadScreen(screenId: Int): Result<ScreenAndAd.Screen> =
        screenDataSource.findById(screenId).mapCatching { screenData ->
            ScreenAndAd.Screen(
                id = screenData.id,
                movie = movieRepository.loadMovie(screenData.movieData.id),
                dateRange = screenData.dateRange,
            )
        }
}

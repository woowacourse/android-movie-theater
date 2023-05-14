package woowacourse.app.presentation.ui.main.home

import woowacourse.app.presentation.model.advertisement.AdvertisementMapper.toUiAdvertisements
import woowacourse.app.presentation.model.movie.MovieMapper.toUiMovies
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository
import woowacourse.domain.movie.MovieRepository

class HomePresenter(
    private val advertisementRepository: AdvertisementRepository,
    private val movieRepository: MovieRepository,
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun fetchHomeData() {
        val movies = movieRepository.getMovies().toUiMovies()
        val advertisements = advertisementRepository.getAdvertisements().toUiAdvertisements()
        view.initMovies(mergeAdvertisement(movies, advertisements))
    }

    private fun <T> mergeAdvertisement(data: List<T>, ads: List<T>): List<T> {
        if (ads.isEmpty()) return data
        val newList = mutableListOf<T>()
        var adsIndex = 0
        data.forEachIndexed { index, it ->
            newList.add(it)
            if ((index % Advertisement.CYCLE) == (Advertisement.CYCLE - 1)) {
                if (adsIndex == ads.size) adsIndex = 0
                newList.add(ads[adsIndex])
                adsIndex++
            }
        }
        return newList
    }
}

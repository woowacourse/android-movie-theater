package woowacourse.app.presentation.usecase.main

import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.model.advertisement.AdvertisementMapper.toUiAdvertisements
import woowacourse.app.presentation.model.movie.MovieMapper.toUiMovies
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository
import woowacourse.domain.movie.MovieRepository

class MainUseCase(
    private val advertisementRepository: AdvertisementRepository,
    private val movieRepository: MovieRepository,
) {
    fun getMainData(): List<HomeData> {
        val movies = movieRepository.getMovies().toUiMovies()
        val advertisements = advertisementRepository.getAdvertisements().toUiAdvertisements()
        return mergeAdvertisement(movies, advertisements)
    }

    private fun <T> mergeAdvertisement(data: List<T>, ads: List<T>): List<T> {
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

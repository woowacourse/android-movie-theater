package woowacourse.app.usecase.main

import woowacourse.app.model.main.AdvertisementMapper.toUiAdvertisements
import woowacourse.app.model.main.MainData
import woowacourse.app.model.main.MovieMapper.toUiMovies
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository
import woowacourse.domain.movie.MovieRepository

class MainUseCase(
    private val advertisementRepository: AdvertisementRepository,
    private val movieRepository: MovieRepository,
) {
    fun getMainData(): List<MainData> {
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

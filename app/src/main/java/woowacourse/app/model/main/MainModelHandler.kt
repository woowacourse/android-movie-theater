package woowacourse.app.model.main

import woowacourse.app.model.main.AdvertisementMapper.toUiAdvertisements
import woowacourse.app.model.main.MovieMapper.toUiMovies
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository
import woowacourse.domain.movie.MovieRepository

object MainModelHandler {
    fun getMainData(): List<MainData> {
        val movies = MovieRepository.getMovies().toUiMovies()
        val advertisements = AdvertisementRepository.getAdvertisements().toUiAdvertisements()
        return mergeAdvertisement(movies, advertisements)
    }

    fun <T> mergeAdvertisement(data: List<T>, ads: List<T>): List<T> {
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

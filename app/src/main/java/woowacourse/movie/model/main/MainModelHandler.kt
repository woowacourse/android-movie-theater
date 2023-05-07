package woowacourse.movie.model.main

import woowacourse.movie.advertisement.Advertisement
import woowacourse.movie.advertisement.AdvertisementRepository
import woowacourse.movie.model.main.AdvertisementMapper.toUiAdvertisements
import woowacourse.movie.model.main.MovieMapper.toUiMovies
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.theater.Theater
import woowacourse.movie.theater.TheaterRepository

object MainModelHandler : MainModelRepository {
    override fun getMainData(): List<MainData> {
        val movies = MovieRepository.getMovies().toUiMovies()
        val advertisements = AdvertisementRepository.getAdvertisements().toUiAdvertisements()
        return mergeAdvertisement(movies, advertisements)
    }

    override fun findMovieById(id: Long): Movie {

        return MovieRepository.getMovie(id)
    }

    override fun findTheaterById(id: Long): Theater {

        return TheaterRepository.getTheater(id)
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

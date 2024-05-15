package woowacourse.movie.presentation.main.home

import woowacourse.movie.data.SampleAdvertisementData
import woowacourse.movie.data.SampleMovieData
import woowacourse.movie.domain.model.home.HomeItem

class HomePresenter(
    val view: HomeContract.View,
) : HomeContract.Presenter {
    private val movieList = SampleMovieData.movieList.map {
        HomeItem.MovieItem(
            it.id,
            it.title,
            it.posterResourceId,
            it.firstScreeningDate,
            it.runningTime,
            it.description
        )
    }
    private val advertisementList = SampleAdvertisementData.advertisementList.map {
        HomeItem.AdvertisementItem(it.resourceId)
    }

    override fun setMoviesInfo() {
        val items: MutableList<HomeItem> = mutableListOf<HomeItem>()
        var adIndex: Int = 0
        movieList.forEachIndexed { index, movie ->
            items.add(movie)
            if ((index + ADS_POSITION_OFFSET) % ADS_INTERVAL == 0 && adIndex < advertisementList.size) {
                items.add(advertisementList[adIndex++])
            }
        }
        view.showMoviesInfo(items)
    }

    companion object {
        private const val ADS_INTERVAL = 3
        private const val ADS_POSITION_OFFSET = 1
    }
}

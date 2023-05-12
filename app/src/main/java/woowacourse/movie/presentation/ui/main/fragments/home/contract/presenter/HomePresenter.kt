package woowacourse.movie.presentation.ui.main.fragments.home.contract.presenter

import woowacourse.movie.R
import woowacourse.movie.domain.model.repository.MovieRepository
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.ui.main.fragments.home.contract.HomeContract

class HomePresenter(
    view: HomeContract.View,
    private val adsInterval: Int = DEFAULT_ADS_INTERVAL,
    private val movieRepository: MovieRepository,
) : HomeContract.Presenter(view) {
    private val listItems = arrayListOf<ListItem>()
    private val adTypes: List<ListItem> = Ad.provideDummy()
    private var lastItemPosition: Int = 0

    override fun loadMoreMoviesWithAds(size: Int) {
        val newLoadedMovies = movieRepository
            .getMovies(size)
            .map { it.toPresentation(R.drawable.img_sample_movie_thumbnail1) }
        val appendItemCount = appendWithAds(newLoadedMovies)

        view.showMoreMovies(listItems.subList(lastItemPosition, listItems.size))
        lastItemPosition += appendItemCount
    }

    private fun appendWithAds(newItems: List<ListItem>): Int {
        var appendItemCount = 0

        newItems.forEach { newMovie ->
            appendItemCount += appendAds()
            if (listItems.add(newMovie)) ++appendItemCount
        }
        return appendItemCount
    }

    private fun appendAds(): Int {
        if ((listItems.size + 1) % (adsInterval + 1) == 0) {
            if (listItems.add(adTypes.random())) return APPENDED_SIZE
        }
        return NO_APPENDED_SIZE
    }

    override fun handleItem(item: ListItem) {
        when (item) {
            is Movie -> view.showTheaterPicker(item)
            is Ad -> view.showAdWebSite(item)
        }
    }

    companion object {
        private const val DEFAULT_ADS_INTERVAL: Int = 3
        private const val APPENDED_SIZE: Int = 1
        private const val NO_APPENDED_SIZE: Int = 0
    }
}

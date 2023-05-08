package woowacourse.movie.presentation.views.main.fragments.home.contract.presenter

import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.views.main.fragments.home.contract.HomeContract

class HomePresenter(view: HomeContract.View) : HomeContract.Presenter(view) {
    private val loadedMovie = mutableSetOf<ListItem>()

    override fun loadAds() {
        view.setupAdView(Ad.provideDummy())
    }

    override fun loadMoreMovies(size: Int) {
        val newLoadedMovies = Movie.provideDummy(size)
        loadedMovie.addAll(newLoadedMovies)

        view.showMoreMovies(newLoadedMovies)
    }

    override fun handleItem(item: ListItem) {
        when (item) {
            is Movie -> view.showTheaterPicker(item)
            is Ad -> view.showAdWebSite(item)
        }
    }
}

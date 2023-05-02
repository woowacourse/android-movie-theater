package woowacourse.movie.presentation.activities.main.fragments.home.contract.presenter

import woowacourse.movie.presentation.activities.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

class HomePresenter(view: HomeContract.View) : HomeContract.Presenter(view) {
    private val loadedMovie = mutableSetOf<ListItem>()

    override fun loadAds(): List<ListItem> = Ad.provideDummy()

    override fun loadMoreMovies(size: Int) {
        val newLoadedMovies = Movie.provideDummy(size)

        loadedMovie.addAll(newLoadedMovies)
        view.showMoreMovies(newLoadedMovies)
    }

    override fun onMovieClick(item: ListItem) {
        when (item) {
            is Movie -> view.showTicketScreen(item)
            is Ad -> view.showAdWebSite(item)
        }
    }
}

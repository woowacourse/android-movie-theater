package woowacourse.movie.presentation.activities.main.fragments.home.contract.presenter

import woowacourse.movie.presentation.activities.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

class HomePresenter : HomeContract.Presenter() {
    private val loadedMovie = mutableSetOf<ListItem>()

    override fun loadAds(): List<ListItem> = Ad.provideDummy()

    override fun loadMoreMovies(size: Int) {
        val newLoadedMovies = Movie.provideDummy(size)

        loadedMovie.addAll(newLoadedMovies)
        requireView().showMoreMovies(newLoadedMovies)
    }

    override fun onMovieClick(item: ListItem) {
        when (item) {
            is Movie -> requireView().showTicketScreen(item)
            is Ad -> requireView().showAdWebSite(item)
        }
    }
}

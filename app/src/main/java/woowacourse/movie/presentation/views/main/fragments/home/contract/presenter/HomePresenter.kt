package woowacourse.movie.presentation.views.main.fragments.home.contract.presenter

import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.views.main.fragments.home.contract.HomeContract

class HomePresenter(view: HomeContract.View) : HomeContract.Presenter(view) {
    private val loadedMovie = mutableSetOf<ListItem>()

    override fun loadAds(): List<ListItem> = Ad.provideDummy()

    override fun loadMoreMovies(size: Int): List<ListItem> {
        val newLoadedMovies = Movie.provideDummy(size)
        loadedMovie.addAll(newLoadedMovies)

        return newLoadedMovies
    }

    override fun onItemClick(item: ListItem) {
        when (item) {
            is Movie -> view.showTheaterPickerScreen(item)
            is Ad -> view.showAdWebSite(item)
        }
    }
}

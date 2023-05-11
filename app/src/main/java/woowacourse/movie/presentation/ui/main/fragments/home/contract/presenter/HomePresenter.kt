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
    private val movieRepository: MovieRepository,
) : HomeContract.Presenter(view) {
    private val loadedMovie = mutableSetOf<ListItem>()

    override fun loadAds() {
        view.setupAdView(Ad.provideDummy())
    }

    override fun loadMoreMovies(size: Int) {
        val newLoadedMovies = movieRepository
            .getMovies(size)
            .map { it.toPresentation(R.drawable.img_sample_movie_thumbnail1) }
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

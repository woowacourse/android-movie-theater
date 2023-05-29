package woowacourse.movie.ui.home

import woowacourse.movie.data.movie.MovieRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: MovieRepository,
) : HomeContract.Presenter {
    override fun setMovieList() {
        val movies = repository.getData()
        view.setMovieList(movies)
    }
}

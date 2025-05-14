package woowacourse.movie.view.movies

import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore

class MovieListPresenterFactory() {
    fun initialize(view: MovieListContract.View): MovieListContract.Presenter {
        val movieStore = MovieStore()
        val theaterStore = TheaterStore(movieStore)
        return MovieListPresenter(view, movieStore, theaterStore)
    }
}

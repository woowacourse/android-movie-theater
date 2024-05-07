package woowacourse.movie.list.presenter

import woowacourse.movie.common.AdvertisementDataSource
import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.list.contract.MovieListContract

class MovieListPresenter(
    val view: MovieListContract.View,
) : MovieListContract.Presenter {
    override val movieList = MovieDataSource.movieList
    private val advertisementList = AdvertisementDataSource.advertisementList
    private val theaterContent = MovieDataSource.theaterContent

    override fun setMoviesInfo() {
        view.showMoviesInfo()
    }

//    override fun updateMoviesInfo() {
//        view.updateMovieEntity(movieList, advertisementList)
//    }

    override fun setMovieListAdapter() {
        view.makeMovieListAdapter(theaterContent)
    }
}

package woowacourse.movie.view.movieList

import woowacourse.movie.entity.Ads
import woowacourse.movie.entity.Movies

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movies: Movies,
    private val ads: Ads,
) : MovieListContract.Presenter {
    override fun loadMovieList() {
        view.setMovieList(movies.getAll(), ads.getAll())
    }
}

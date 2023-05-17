package woowacourse.movie.presentation.view.main.home

import woowacourse.movie.data.MovieData
import woowacourse.movie.presentation.model.Movie

class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {
    override fun getMovieData() {
        val movies = MovieData.getData()
        view.setRecyclerView(movies.map {
            Movie.from(it)
        })
    }
}
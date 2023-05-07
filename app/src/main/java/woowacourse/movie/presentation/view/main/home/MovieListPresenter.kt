package woowacourse.movie.presentation.view.main.home

import woowacourse.movie.data.MovieData

class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {
    override fun getMovieData() {
        val movies = MovieData.getData()
        view.setRecyclerView(movies)
    }
}
package woowacourse.movie.view.movieList

import woowacourse.movie.model.AdModel
import woowacourse.movie.model.MovieModel

class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {
    private lateinit var movies: List<MovieModel>
    private lateinit var ads: List<AdModel>

    override fun setupMovieList(movies: List<MovieModel>, ads: List<AdModel>) {
        this.movies = movies
        this.ads = ads
    }

    override fun loadMovieList() {
        view.setMovieList(movies, ads)
    }
}

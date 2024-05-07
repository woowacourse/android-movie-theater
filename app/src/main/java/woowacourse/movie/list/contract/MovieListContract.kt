package woowacourse.movie.list.contract

import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.model.TheaterContent

interface MovieListContract {
    interface View {
        val presenter: Presenter

        fun showMoviesInfo()

//        fun updateMovieEntity(movieList: List<Movie>, advertisementList: List<Advertisement>)
        fun makeMovieListAdapter(theaterContent: List<TheaterContent>)
    }

    interface Presenter {
        val movieList: List<Movie>

        fun setMoviesInfo()
        fun setMovieListAdapter()
//        fun updateMoviesInfo()
    }
}

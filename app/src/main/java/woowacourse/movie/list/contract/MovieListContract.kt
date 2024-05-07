package woowacourse.movie.list.contract

import woowacourse.movie.list.model.TheaterContent

interface MovieListContract {
    interface View {
        val presenter: Presenter

        fun showMoviesInfo()

        fun updateMovieEntity(theaterContent: List<TheaterContent>)
        fun makeMovieListAdapter(theaterContent: List<TheaterContent>)
    }

    interface Presenter {
        fun setMoviesInfo()
        fun setMovieListAdapter()
        fun updateMoviesInfo()
    }
}

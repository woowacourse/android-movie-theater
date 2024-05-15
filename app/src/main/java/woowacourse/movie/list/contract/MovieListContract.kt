package woowacourse.movie.list.contract

import woowacourse.movie.list.model.TheaterContent

interface MovieListContract {
    interface View {
        val presenter: Presenter

        fun showMoviesList()

        fun updateMovieItems(theaterContent: List<TheaterContent>)
        fun linkMovieListAdapter(theaterContent: List<TheaterContent>)
    }

    interface Presenter {
        fun setMoviesInfo()
        fun setMovieListAdapter()
        fun updateMoviesInfo()
    }
}

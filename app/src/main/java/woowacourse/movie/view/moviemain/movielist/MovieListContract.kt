package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieListModel

interface MovieListContract {
    interface Presenter {
        fun getMovieListData(): List<MovieListModel>
    }
}

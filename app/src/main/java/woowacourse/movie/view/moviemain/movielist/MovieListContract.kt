package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieUiModel

interface MovieListContract {
    interface Presenter {
        fun getMovieListData(): List<MovieUiModel>
    }
}

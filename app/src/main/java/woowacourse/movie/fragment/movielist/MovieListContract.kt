package woowacourse.movie.fragment.movielist

import woowacourse.movie.view.data.MovieViewDatas

interface MovieListContract {

    interface View {
        var presenter: Presenter
        fun setMovieListData(movieViewDatas: MovieViewDatas)
    }

    interface Presenter {
        fun loadMovieListData()
    }
}

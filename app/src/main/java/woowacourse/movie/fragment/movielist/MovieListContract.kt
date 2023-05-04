package woowacourse.movie.fragment.movielist

import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieViewDatas

interface MovieListContract {

    interface View {
        var presenter: Presenter
        fun initMovieRecyclerView(movieViewDatas: MovieViewDatas)
        fun onMovieClick(data: MovieListViewData)
    }

    interface Presenter {
        fun initMovieRecyclerView()
        fun onItemClick(data: MovieListViewData)
    }
}

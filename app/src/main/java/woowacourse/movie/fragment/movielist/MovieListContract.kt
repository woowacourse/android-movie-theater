package woowacourse.movie.fragment.movielist

import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieViewDatas

interface MovieListContract {

    interface View {
        var presenter: Presenter
        fun makeMovieRecyclerView(movieViewDatas: MovieViewDatas)
        fun setOnMovieClickListener(data: MovieListViewData)
    }

    interface Presenter {
        fun makeMovieRecyclerView()
        fun setOnClickListener(data: MovieListViewData)
    }
}

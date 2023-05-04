package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieListModel

interface MovieListContract {
    interface View {
        var presenter: Presenter
        fun showMovieList(dataList: List<MovieListModel>)
        fun openReservationActivity(item: MovieListModel.MovieUiModel)
        fun openAdPage(item: MovieListModel.MovieAdModel)
    }

    interface Presenter {
        fun loadMovieList()
        fun onItemClick(item: MovieListModel)
    }
}

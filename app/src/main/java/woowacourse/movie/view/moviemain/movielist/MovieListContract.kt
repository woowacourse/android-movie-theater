package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.MovieTheater

interface MovieListContract {
    interface View {
        var presenter: Presenter
        fun showMovieList(dataList: List<MovieListModel>)
        fun openTheaterBottomSheet(theaters: List<MovieTheater>, movie: MovieListModel.MovieUiModel)
        fun openReservationActivity(item: MovieListModel.MovieUiModel, movieTheater: MovieTheater)
        fun openAdPage(item: MovieListModel.MovieAdModel)
    }

    interface Presenter {
        fun loadMovieList()
        fun loadTheaterList(movie: MovieListModel.MovieUiModel)
        fun onItemClick(item: MovieListModel)
    }
}

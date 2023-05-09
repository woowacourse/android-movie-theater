package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.MovieTheater

interface MovieListContract {
    interface View {
        var presenter: Presenter
        fun showMovieList(dataList: List<MovieListModel>)
        fun showTheaterList(theaters: List<MovieTheater>, movie: MovieListModel.MovieUiModel)
        fun toReservationScreen(item: MovieListModel.MovieUiModel, movieTheater: MovieTheater)
        fun toAdScreen(item: MovieListModel.MovieAdModel)
    }

    interface Presenter {
        fun loadMovieList()
        fun loadTheaterList(movie: MovieListModel.MovieUiModel)
        fun decideNextAction(item: MovieListModel)
    }
}

package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.domain.Theater
import woowacourse.movie.view.model.MovieListModel

interface MovieListContract {
    interface View {
        var presenter: Presenter
        fun showMovieList(dataList: List<MovieListModel>)
        fun openTheaterBottomSheet(theaters: List<Theater>)
        fun openReservationActivity(item: MovieListModel.MovieUiModel, theaterName: String)
        fun openAdPage(item: MovieListModel.MovieAdModel)
    }

    interface Presenter {
        fun loadMovieList()
        fun loadTheaterList(movieId: Int)
        fun onItemClick(item: MovieListModel)
    }
}

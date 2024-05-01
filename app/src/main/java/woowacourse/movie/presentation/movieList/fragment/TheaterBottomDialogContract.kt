package woowacourse.movie.presentation.movieList.fragment

import woowacourse.movie.model.Theater

interface TheaterBottomDialogContract {
    interface View {
        fun showTheaterList(theaterList: List<Theater>)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)
    }
}

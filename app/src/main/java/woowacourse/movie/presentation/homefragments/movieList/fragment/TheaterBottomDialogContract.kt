package woowacourse.movie.presentation.homefragments.movieList.fragment

import woowacourse.movie.model.Theater

interface TheaterBottomDialogContract {
    interface View {
        fun showTheaterList(theaterList: List<Theater>)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)
    }
}

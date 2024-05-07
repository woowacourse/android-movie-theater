package woowacourse.movie.presentation.homefragments.movieList.fragment

import woowacourse.movie.presentation.homefragments.movieList.uimodel.ScreeningTheater

interface TheaterBottomDialogContract {
    interface View {
        fun showTheaterList(screeningTheaters: List<ScreeningTheater>)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)
    }
}

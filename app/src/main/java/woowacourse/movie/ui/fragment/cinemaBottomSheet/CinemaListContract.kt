package woowacourse.movie.ui.fragment.cinemaBottomSheet

import woowacourse.movie.model.CinemaState

interface CinemaListContract {
    interface View {
        fun setAdapter(cinemas: List<CinemaState>)
    }
    interface Presenter {
        fun getCinemaList()
    }
}

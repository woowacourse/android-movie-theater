package woowacourse.movie.view.movieList.bottomSheet

import woowacourse.movie.model.TheaterModel

interface TheaterSheetContract {
    interface View {
        var presenter: Presenter

        fun setTheaterAdapter(adapter: TheaterSheetAdapter)
    }

    interface Presenter {
        fun setAdapter(theaters: List<TheaterModel>, moveToActivity: (TheaterModel) -> Unit)
    }
}

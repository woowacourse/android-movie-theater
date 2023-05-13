package woowacourse.movie.view.activities.home.fragments.screeninglist

interface ScreeningListContract {
    interface Presenter {
        fun loadScreenings()
    }

    interface View {
        fun setScreeningList(screeningListViewItemUIStates: List<ScreeningListViewItemUIState>)
    }
}

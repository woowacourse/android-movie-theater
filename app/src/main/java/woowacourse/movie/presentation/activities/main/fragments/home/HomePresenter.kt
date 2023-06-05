package woowacourse.movie.presentation.activities.main.fragments.home

import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.model.item.Theater

class HomePresenter(
    val view: HomeContract.View,
) : HomeContract.Presenter {

    override fun loadData() {
        view.showMovieList(Ad.provideDummy())
    }

    override fun moveNext(item: ListItem) {
        when (item) {
            is Movie -> view.moveTheaterList(item)
            is Ad -> view.moveAdWebPage(item)
            is Reservation -> {}
            is Theater -> {}
        }
    }
}

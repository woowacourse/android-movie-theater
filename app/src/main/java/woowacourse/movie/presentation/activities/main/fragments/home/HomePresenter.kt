package woowacourse.movie.presentation.activities.main.fragments.home

import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie

class HomePresenter(
    val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun onClickedMovie(movie: Movie) {
        view.startTicketingActivity(movie)
    }

    override fun onClickedAd(ads: Ad) {
        view.accessAdWebPage(ads)
    }
}

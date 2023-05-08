package woowacourse.movie.presentation.activities.main.fragments.home

import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie

class HomePresenter(
    val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun onMovieClicked(movie: Movie) {
        view.showTheaterList(movie)
    }

    override fun onAdClicked(ads: Ad) {
        view.moveAdWebPage(ads)
    }
}

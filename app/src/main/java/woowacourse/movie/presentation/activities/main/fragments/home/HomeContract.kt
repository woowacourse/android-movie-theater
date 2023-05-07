package woowacourse.movie.presentation.activities.main.fragments.home

import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun selectTheater(movie: Movie)
        fun startTicketingActivity(movie: Movie)
        fun accessAdWebPage(ads: Ad)
    }

    interface Presenter {
        fun onClickedMovie(movie: Movie)
        fun onClickedAd(ads: Ad)
    }
}

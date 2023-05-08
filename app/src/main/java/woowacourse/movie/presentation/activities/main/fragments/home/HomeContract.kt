package woowacourse.movie.presentation.activities.main.fragments.home

import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun showTheaterList(movie: Movie)
        fun moveAdWebPage(ads: Ad)
    }

    interface Presenter {
        fun onMovieClicked(movie: Movie)
        fun onAdClicked(ads: Ad)
    }
}

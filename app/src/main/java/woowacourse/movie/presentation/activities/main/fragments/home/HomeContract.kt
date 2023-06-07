package woowacourse.movie.presentation.activities.main.fragments.home

import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun moveTheaterList(movie: Movie)
        fun moveAdWebPage(ads: Ad)
        fun showMovieList(movies: List<ListItem>)
    }

    interface Presenter {
        fun loadData()
        fun moveNext(item: ListItem)
    }
}

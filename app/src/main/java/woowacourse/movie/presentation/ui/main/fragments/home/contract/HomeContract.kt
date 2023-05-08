package woowacourse.movie.presentation.ui.main.fragments.home.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

interface HomeContract {
    interface View : BaseContract.View {
        fun setupAdView(ads: List<ListItem>)
        fun showMoreMovies(items: List<Movie>)
        fun showAdWebSite(item: Ad)
        fun showTheaterPicker(item: Movie)
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun loadMoreMovies(size: Int = DEFAULT_LOAD_SIZE)
        abstract fun loadAds()
        abstract fun handleItem(item: ListItem)
    }

    companion object {
        private const val DEFAULT_LOAD_SIZE = 20
    }
}

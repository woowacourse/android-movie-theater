package woowacourse.movie.presentation.ui.main.fragments.home.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

interface HomeContract {
    interface View : BaseContract.View {
        fun showMoreMovies(items: List<ListItem>)
        fun showAdWebSite(item: Ad)
        fun showTheaterPicker(item: Movie)
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun loadMoreMoviesWithAds(size: Int = DEFAULT_LOAD_SIZE)
        abstract fun handleItem(item: ListItem)
    }

    companion object {
        private const val DEFAULT_LOAD_SIZE = 20
    }
}

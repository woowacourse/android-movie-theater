package woowacourse.movie.presentation.views.main.fragments.home.contract

import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun showTheaterPickerScreen(item: Movie)
        fun showAdWebSite(item: Ad)
    }

    abstract class Presenter {
        private var view: View? = null

        fun attach(view: View) {
            this.view = view
        }

        fun detach() {
            this.view = null
        }

        fun requireView(): View = view ?: throw IllegalStateException("View is not attached")

        abstract fun loadMoreMovies(size: Int = DEFAULT_LOAD_SIZE): List<ListItem>
        abstract fun loadAds(): List<ListItem>
        abstract fun onItemClick(item: ListItem)
    }

    companion object {
        private const val DEFAULT_LOAD_SIZE = 20
    }
}

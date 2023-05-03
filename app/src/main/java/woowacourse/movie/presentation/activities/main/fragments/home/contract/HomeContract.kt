package woowacourse.movie.presentation.activities.main.fragments.home.contract

import woowacourse.movie.presentation.model.movieitem.ListItem

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun showMoreMovies(items: List<ListItem>)
        fun showTicketScreen(item: ListItem)
        fun showAdWebSite(item: ListItem)
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

        abstract fun loadMoreMovies(size: Int = DEFAULT_LOAD_SIZE)
        abstract fun loadAds(): List<ListItem>
        abstract fun onMovieClick(item: ListItem)
    }

    companion object {
        private const val DEFAULT_LOAD_SIZE = 20
    }
}

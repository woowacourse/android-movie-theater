package woowacourse.movie.fragment.movielist

import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(recyclerViewAdapter: MovieRecyclerViewAdapter)
    }

    interface Presenter {
        fun setMovieRecyclerView(onClickMovie: (Int) -> Unit, onClickAd: (AdUIModel) -> Unit)
    }
}

package woowacourse.movie.fragment.movielist

import com.woowacourse.domain.Theater
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.fragment.movielist.adapter.TheaterRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(recyclerViewAdapter: MovieRecyclerViewAdapter)
        fun setTheaterRecyclerView(recyclerViewAdapter: TheaterRecyclerViewAdapter)
    }

    interface Presenter {
        fun setMovieRecyclerView(onClickMovie: (Int) -> Unit, onClickAd: (AdUIModel) -> Unit)
        fun setTheaterRecyclerView(theaters: List<Theater>, onClickTheater: (Int) -> Unit)
    }
}

package woowacourse.movie.fragment.movielist

import com.woowacourse.domain.TheaterMovie
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.MovieUIModel

interface HomeContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(movies: List<MovieUIModel>, ad: AdUIModel)
        fun setTheaterRecyclerView(theaters: List<TheaterMovie>)
    }

    interface Presenter {
        fun setMovieRecyclerView()
        fun setTheaterRecyclerView(position: Int)
    }
}

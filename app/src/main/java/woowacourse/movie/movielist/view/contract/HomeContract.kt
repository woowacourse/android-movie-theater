package woowacourse.movie.movielist.view.contract

import woowacourse.movie.movielist.dto.AdDto
import woowacourse.movie.movielist.dto.MovieDto

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun setUpMovieData(movies: List<MovieDto>, ads: AdDto)
    }

    interface Presenter {
        fun initData()
    }
}

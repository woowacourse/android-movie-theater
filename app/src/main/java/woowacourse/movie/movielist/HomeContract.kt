package woowacourse.movie.movielist

import woowacourse.movie.dto.AdDto
import woowacourse.movie.dto.movie.MovieDto

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun setUpMovieData(movies: List<MovieDto>, ads: AdDto)
    }

    interface Presenter {
        fun initFragment()
    }
}

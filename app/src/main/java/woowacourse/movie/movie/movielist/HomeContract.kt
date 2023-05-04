package woowacourse.movie.movie.movielist

import woowacourse.movie.dto.AdDto
import woowacourse.movie.movie.dto.movie.MovieDto

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun setUpMovieData(movies: List<MovieDto>, ads: AdDto)
    }

    interface Presenter {
        fun initFragment()
    }
}

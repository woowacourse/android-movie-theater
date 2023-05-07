package woowacourse.movie.movie.theater

import woowacourse.movie.movie.dto.movie.MovieDto

interface TheaterContract {
    interface View {
        val presenter: Presenter

        fun initDetailData()
        fun setUpTheaterData(movie: MovieDto)
    }

    interface Presenter {
        var movie: MovieDto

        fun initFragment(data: MovieDto)
    }
}
